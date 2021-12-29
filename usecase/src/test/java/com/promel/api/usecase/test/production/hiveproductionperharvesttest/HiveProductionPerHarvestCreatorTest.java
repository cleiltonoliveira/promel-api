package com.promel.api.usecase.test.production.hiveproductionperharvesttest;

import com.promel.api.domain.model.*;
import com.promel.api.usecase.exception.CustomForbiddenException;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.production.hiveproductionperharvest.HiveProductionPerHarvestCreator;
import com.promel.api.usecase.production.hiveproductionperharvest.adapter.HiveProductionPerHarvestAdapter;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionFinder;
import com.promel.api.usecase.user.UserAccountFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HiveProductionPerHarvestCreatorTest {

    @InjectMocks
    private HiveProductionPerHarvestCreator creator;
    @Mock
    private HiveProductionPerHarvestAdapter adapter;
    @Mock
    private HoneyProductionFinder honeyProductionFinder;
    @Mock
    private UserAccountFinder userAccountFinder;

    @Test
    public void createWhenSuccessfulShouldReturnModel() {

        when(adapter.save(any(HiveProductionPerHarvest.class))).thenAnswer(ans -> ans.getArgument(0));
        when(userAccountFinder.findByEmail(anyString())).thenReturn(getUserAccount());
        when(honeyProductionFinder.findById(anyLong())).thenReturn(mock(HoneyProduction.class));
        when(adapter.associationHasHoneyProductionAndHive(anyLong(), anyLong())).thenReturn(true);

        var hpph = creator.create(buildModel(), "test@email.com");
        assertNotNull(hpph);
    }

    @Test
    @DisplayName("create When HoneyProduction And Hive Don't Belong To The Same Association Should Throw ResourceConflictException")
    public void createWhenHoneyProductionAndHiveDontBelongToTheSameAssociationShouldThrowResourceConflictException() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(getUserAccount());
        when(honeyProductionFinder.findById(anyLong())).thenReturn(mock(HoneyProduction.class));
        when(adapter.associationHasHoneyProductionAndHive(anyLong(), anyLong())).thenReturn(false);

        assertThrows(ResourceConflictException.class, () -> creator.create(buildModel(), "test@email.com"));
    }

    @Test
    @DisplayName("create When HoneyProduction Doesn't Belong To The User Association Should Throw CustomForbiddenException")
    public void createWhenHoneyProductionDoesntBelongToTheUserAssociationShouldThrowCustomForbiddenException() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(getUserAccount());
        when(honeyProductionFinder.findById(anyLong())).thenReturn(new HoneyProduction());

        assertThrows(CustomForbiddenException.class, () -> creator.create(buildModel(), "test@email.com"));
    }

    @Test
    @DisplayName("create When The Harvest Is Over Should Throw ResourceConflictException")
    public void createWhenTheHarvestIsOverShouldThrowResourceConflictException() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(getUserAccount());
        when(honeyProductionFinder.findById(anyLong())).thenAnswer(ans -> {
            var hp = spy(HoneyProduction.class);
            hp.setEndDate(LocalDateTime.now());
            hp.setAssociationId(0L);
            return hp;
        });

        assertThrows(ResourceConflictException.class, () -> creator.create(buildModel(), "test@email.com"));
    }

    private HiveProductionPerHarvest buildModel() {
        var key = new HiveProductionPerHarvestKey(1L, 1L);
        return new HiveProductionPerHarvest(key, 10, 0.0);
    }

    private UserAccount getUserAccount() {
        var account = new UserAccount();
        account.setAssociation(mock(Association.class));
        return account;
    }
}
