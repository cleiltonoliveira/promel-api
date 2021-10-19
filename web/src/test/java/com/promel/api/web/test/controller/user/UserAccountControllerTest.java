package com.promel.api.web.test.controller.user;

import com.promel.api.domain.model.Association;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.association.AssociationCreator;
import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.web.controller.user.dto.UserAccountCreationRequest;
import com.promel.api.web.test.controller.MySQLTestContainerConfig;
import com.promel.api.web.test.controller.SetupWebTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.promel.api.web.test.controller.helper.JsonHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SetupWebTest
@Transactional
public class UserAccountControllerTest extends MySQLTestContainerConfig {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountCreator userAccountCreator;
    @Autowired
    private AssociationCreator associationCreator;

    @BeforeAll
    private void setup() {
        createUser();
        createAssociation();
    }

    private void createUser() {
        var account = new UserAccount();
        var userAuth = new UserAuth();
        userAuth.setEmail("BilboBaggins@ohobbit.com");
        userAuth.setPassword("BilboBaggins");
        account.setName("Bilbo");
        account.setUserAuth(userAuth);

        var user = userAccountCreator.create(account);

        assertNotNull(user.getId());
        assertEquals("BilboBaggins@ohobbit.com", user.getUserAuth().getEmail());
    }

    private void createAssociation() {
        var association = new Association();
        association.setInviteCode("testInviteCode");
        association.setName("testAssociation");
        association.setCnpj("02.616.289/0001-01");
        assertNotNull(associationCreator.create(association).getId());
    }

    private UserAccountCreationRequest buildRequestModel() {
        var model = new UserAccountCreationRequest();
        model.setName("Gloin");
        model.setUserAuthEmail("gloin@ohobbit.com");
        model.setUserAuthPassword("gloin");
        return model;
    }

    @Test
    @DisplayName("create when success should return status code 201")
    public void createWhenSuccessShouldReturnStatusCode201() throws Exception {
        var model = buildRequestModel();
        mockMvc.perform(post("/api/v1/public/users")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(model.getName()))
                .andExpect(jsonPath("$.email").value(model.getUserAuthEmail()))
                .andExpect(jsonPath("$.creationDate").isNotEmpty());
    }

    @Test
    @DisplayName("create when name is null should return status code 400")
    public void createWhenNameIsNullShouldReturnStatusCode400() throws Exception {
        var model = buildRequestModel();
        model.setName(null);
        mockMvc.perform(post("/api/v1/public/users")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("create when email is null should return status code 400")
    public void createWhenEmailIsNullShouldReturnStatusCode400() throws Exception {
        var model = buildRequestModel();
        model.setUserAuthEmail(null);
        mockMvc.perform(post("/api/v1/public/users")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("join association when success should return status code 204")
    public void joinAssociationWhenSuccessShouldReturnStatusCode204() throws Exception {
        mockMvc.perform(patch("/api/v1/protected/users/association").param("inviteCode", "testInviteCode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("join association when user is already associated should return status code 409")
    public void joinAssociationWhenUserIsAlreadyAssociatedShouldReturnStatusCode409() throws Exception {
        mockMvc.perform(patch("/api/v1/protected/users/association").param("inviteCode", "testInviteCode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(patch("/api/v1/protected/users/association").param("inviteCode", "testInviteCode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("join association when invite code does not exists should return status code 404")
    public void joinAssociationWhenInviteCodeNotFoundShouldReturnStatusCode404() throws Exception {
        mockMvc.perform(patch("/api/v1/protected/users/association").param("inviteCode", "wrongInviteCode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
