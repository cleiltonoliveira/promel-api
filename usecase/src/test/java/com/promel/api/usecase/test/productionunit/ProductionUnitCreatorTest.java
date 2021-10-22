package com.promel.api.usecase.test.productionunit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.productiounit.ProductionUnitCreator;
import com.promel.api.usecase.productiounit.adapter.ProductionUnitAdapter;
import com.promel.api.usecase.user.UserAccountFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductionUnitCreatorTest {
    @InjectMocks
    private ProductionUnitCreator productionUnitCreator;
    @Mock
    private ProductionUnitAdapter adapter;
    @Mock
    private UserAccountFinder userAccountFinder;

    @Test
    public void createWhenSuccessfulShouldReturnModel() {
        when(adapter.save(any(ProductionUnit.class))).then(ans -> ans.getArgument(0));
        when(userAccountFinder.findByEmail(anyString())).thenReturn(mock(UserAccount.class));

        var unit = productionUnitCreator.create("mockuserEmail@Gmail.com");

        assertNotNull(unit);
        assertNotNull(unit.getUserAccountId());
        assertNotNull(unit.getLastModificationDate());
    }
}
