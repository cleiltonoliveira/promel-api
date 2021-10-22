package com.promel.api.web.test.controller.productionunit;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.web.test.controller.MySQLTestContainerConfig;
import com.promel.api.web.test.controller.SetupWebTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SetupWebTest
public class ProductionUnitControllerTest extends MySQLTestContainerConfig {

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
        assertEquals("BilboBaggins@ohobbit.com", user.getUserAuth().getEmail());
    }

    @Test
    @WithMockUser(roles = {"ASSOCIATION_USER"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when success should return status code 201")
    public void createWhenSuccessShouldReturnStatusCode201() throws Exception {
        mockMvc.perform(post("/api/v1/protected/production-units"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.lastModificationDate").isNotEmpty())
                .andExpect(jsonPath("$.totalProduction").isNumber());
    }

    @Test
    @WithMockUser(roles = {"WRONG_ROLE"}, username = "BilboBaggins@ohobbit.com")
    @DisplayName("create when user is unauthorized should return status code 403")
    public void createWhenUserIsUnauthorizedShouldStatusCode403() throws Exception {
        mockMvc.perform(post("/api/v1/protected/production-units"))
                .andExpect(status().isForbidden());
    }
}
