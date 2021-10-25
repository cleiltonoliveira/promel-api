package com.promel.api.web.test.controller.hive;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.productiounit.ProductionUnitCreator;
import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.web.controller.hive.dto.HiveCreationRequest;
import com.promel.api.web.test.controller.MySQLTestContainerConfig;
import com.promel.api.web.test.controller.SetupWebTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SetupWebTest
@Transactional
public class HiveControllerTest extends MySQLTestContainerConfig {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductionUnitCreator productionUnitCreator;
    @Autowired
    private UserAccountCreator userAccountCreator;

    private final String TEST_EMAIL = "BilboBaggins@ohobbit.com";
    private Long productionUnitId;

    @BeforeAll
    private void setup() {
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

    @BeforeEach
    private void createProductionUnit() {
        if (productionUnitId == null) {
            productionUnitId = productionUnitCreator.create(TEST_EMAIL).getId();
            assertNotNull(productionUnitId);
        }
    }

    @Test
    @WithMockUser(roles = "ASSOCIATION_USER", username = TEST_EMAIL)
    @DisplayName("create when success should return status code 201")
    public void createWhenSuccessShouldReturnStatusCode201() throws Exception {
        var model = buildRequestModel();
        model.setProductionUnitId(productionUnitId);

        mockMvc.perform(post("/api/v1/protected/hives")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.creationDate").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ASSOCIATION_USER", username = TEST_EMAIL)
    @DisplayName("create when identification code is null should return status code 400")
    public void createWhenIdentificationCodeIsNullShouldReturnStatusCode400() throws Exception {

        var model = new HiveCreationRequest();
        model.setProductionUnitId(productionUnitId);

        mockMvc.perform(post("/api/v1/protected/hives")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ASSOCIATION_USER", username = TEST_EMAIL)
    @DisplayName("create when production unit id is null should return status code 400")
    public void createWhenProductionUnitIdIsNullShouldReturnStatusCode400() throws Exception {

        var model = buildRequestModel();

        mockMvc.perform(post("/api/v1/protected/hives")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private HiveCreationRequest buildRequestModel() {
        var model = new HiveCreationRequest();
        model.setIdentificationCode("CA1");
        return model;
    }
}
