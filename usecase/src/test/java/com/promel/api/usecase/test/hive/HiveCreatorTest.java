package com.promel.api.usecase.test.hive;

import com.promel.api.domain.model.Hive;
import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.exception.CustomForbiddenException;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.hive.HiveCreator;
import com.promel.api.usecase.hive.adapter.HiveAdapter;
import com.promel.api.usecase.productiounit.ProductionUnitFinder;
import com.promel.api.usecase.user.UserAccountFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HiveCreatorTest {

    @InjectMocks
    private HiveCreator hiveCreator;
    @Mock
    private HiveAdapter adapter;
    @Mock
    private ProductionUnitFinder productionUnitFinder;
    @Mock
    private UserAccountFinder userAccountFinder;

    @Test
    public void createWhenSuccessfulShouldReturnModel() {
        var model = buildModel();

        when(adapter.save(any(Hive.class))).then(ans -> ans.getArgument(0));
        when(userAccountFinder.findByEmail(anyString())).thenReturn(mock(UserAccount.class));
        when(productionUnitFinder.findById(anyLong())).thenReturn(mock(ProductionUnit.class));

        var hive = hiveCreator.create(model, "saruman@ohobbit.com");

        assertNotNull(hive);
        assertNotNull(hive.getCreationDate());
    }

    @Test
    @DisplayName("create when user is not the production unit own should throw CustomForbiddenException")
    public void createWhenUserIsNotProductionUnitOwnShouldThrowCustomForbiddenException() {
        var model = buildModel();

        when(userAccountFinder.findByEmail(anyString())).then(ans -> {
            var user = new UserAccount();
            user.setId(1L);
            return user;
        });
        when(productionUnitFinder.findById(anyLong())).then(ans -> {
            var unit = new ProductionUnit();
            unit.setUserAccountId(0L);
            return unit;
        });

        assertThrows(CustomForbiddenException.class, () -> hiveCreator.create(model, "saruman@ohobbit.com"));
    }

    @Test
    @DisplayName("create when hive already exists should throw ResourceConflictException")
    public void createWhenHiveAlreadyExistsShouldThrowResourceConflictException() {
        var model = buildModel();
        when(userAccountFinder.findByEmail(anyString())).thenReturn(mock(UserAccount.class));
        when(productionUnitFinder.findById(anyLong())).thenReturn(mock(ProductionUnit.class));
        when(adapter.existsByIdentificationCodeAndProductionUnitId(anyString(), anyLong())).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> hiveCreator.create(model, "saruman@ohobbit.com"));
    }

    private Hive buildModel() {
        var model = new Hive();
        model.setIdentificationCode("TestIdCode");
        model.setProductionUnitId(1L);
        return model;
    }
}
