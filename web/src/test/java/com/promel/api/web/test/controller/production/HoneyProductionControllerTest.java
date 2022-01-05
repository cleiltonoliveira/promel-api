package com.promel.api.web.test.controller.production;

import com.promel.api.domain.model.Association;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.user.UserAccountCreator;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SetupWebTest
@Transactional
public class HoneyProductionControllerTest extends MySQLTestContainerConfig {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountCreator userAccountCreator;

    private final String TEST_EMAIL = "BilboBaggins@ohobbit.com";

    @BeforeAll
    private void setup() {

        createUserAccount();
        createAssociation();
    }

    private void createUserAccount() {
        var account = new UserAccount();
        var userAuth = new UserAuth();
        userAuth.setEmail(TEST_EMAIL);
        userAuth.setPassword("BilboBaggins");
        account.setName("Bilbo");
        account.setUserAuth(userAuth);

        var user = userAccountCreator.create(account);

        assertNotNull(user.getId());
        assertEquals(TEST_EMAIL, user.getUserAuth().getEmail());
    }

    public void createAssociation() {
        var association = new Association();
        association.setCnpj("68.076.324/0001-47");
        association.setName("adrap");

        try {
            mockMvc.perform(post("/api/v1/protected/associations")
                            .with(user(TEST_EMAIL).roles("ASSOCIATION_USER"))
                            .content(asJsonString(association))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_ADMIN"}, username = TEST_EMAIL)
    @DisplayName("create when success should return status code 201")
    public void createWhenSuccessShouldReturnStatusCode201() throws Exception {
        mockMvc.perform(post("/api/v1/admin/association/production"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.harvestDate").isNotEmpty())
                .andExpect(jsonPath("$.associationId").isNumber());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = TEST_EMAIL)
    @DisplayName("findHoneyProductionInProgress When Success Should Return Status Code 200 And Body")
    public void findHoneyProductionInProgressWhenSuccessShouldReturnStatusCode200AndBody() throws Exception {
        mockMvc.perform(post("/api/v1/admin/association/production")
                        .with(user(TEST_EMAIL).roles("ASSOCIATION_ADMIN")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
        mockMvc.perform(get("/api/v1/protected/association/production/in-progress"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.endDate").isEmpty())
                .andExpect(jsonPath("$.associationId").isNumber());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = TEST_EMAIL)
    @DisplayName("findAllHoneyProductionByAssociationShouldReturnStatusCode200AndBody")
    public void findAllHoneyProductionByAssociationShouldReturnStatusCode200AndBody() throws Exception {
        mockMvc.perform(post("/api/v1/admin/association/production")
                        .with(user(TEST_EMAIL).roles("ASSOCIATION_ADMIN")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
        mockMvc.perform(get("/api/v1/protected/association/production/history"))
                .andExpect(status().isOk());
    }
}
