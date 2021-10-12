package com.promel.api.web.test.controller.association;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.web.controller.association.dto.AssociationCreationRequest;
import com.promel.api.web.test.controller.MySQLTestContainerConfig;
import com.promel.api.web.test.controller.SetupWebTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SetupWebTest
@Transactional
public class AssociationControllerTest extends MySQLTestContainerConfig {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountCreator userAccountCreator;

    @BeforeAll
    private void setup() {
        var account = new UserAccount();
        var userAuth = new UserAuth();
        userAuth.setEmail("BilboBaggins@ohobbit.com");
        userAuth.setPassword("BilboBaggins");
        account.setName("Bilbo");
        account.setUserAuth(userAuth);

        var user = userAccountCreator.create(account);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Bilbo", user.getName());
        assertNotNull(user.getUserAuth().getId());
        assertEquals("BilboBaggins@ohobbit.com", user.getUserAuth().getEmail());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when success should return status code 201")
    public void createWhenSuccessShouldReturnStatusCode201() throws Exception {
        var model = buildRequestModel();
        mockMvc.perform(post("/api/v1/protected/associations")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(model.getName()))
                .andExpect(jsonPath("$.cnpj").value(model.getCnpj()))
                .andExpect(jsonPath("$.creationDate").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when cnpj already exists should return status code 409")
    public void createWhenCnpjAlreadyExistsShouldReturnStatusCode409() throws Exception {
        var model = buildRequestModel();
        mockMvc.perform(post("/api/v1/protected/associations")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.cnpj").value(model.getCnpj()));

        mockMvc.perform(post("/api/v1/protected/associations")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when name is null should return status code 400")
    public void createWhenNameIsNullShouldReturnStatusCode400() throws Exception {
        var model = buildRequestModel();

        model.setName(null);
        mockMvc.perform(post("/api/v1/protected/associations")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when cnpj is null should return status code 400")
    public void createWhenCnpjIsNullShouldReturnStatusCode400() throws Exception {
        var model = buildRequestModel();

        model.setCnpj(null);
        mockMvc.perform(post("/api/v1/protected/associations")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"WRONG_ROLE"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when user is unauthorized should return status code 403")
    public void createWhenUserIsUnauthorizedShouldStatusCode403() throws Exception {
        var model = buildRequestModel();

        mockMvc.perform(post("/api/v1/protected/associations")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private AssociationCreationRequest buildRequestModel() {
        var model = new AssociationCreationRequest();
        model.setName("ADRAP");
        model.setCnpj("02.616.289/0001-01");
        return model;
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
