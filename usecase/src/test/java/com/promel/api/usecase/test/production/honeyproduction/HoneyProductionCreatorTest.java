package com.promel.api.usecase.test.production.honeyproduction;

import com.promel.api.domain.model.Association;
import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionCreator;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionFinder;
import com.promel.api.usecase.production.honeyproduction.adapter.HoneyProductionAdapter;
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
public class HoneyProductionCreatorTest {

    @InjectMocks
    private HoneyProductionCreator creator;
    @Mock
    private HoneyProductionAdapter adapter;
    @Mock
    private UserAccountFinder userAccountFinder;
    @Mock
    private HoneyProductionFinder honeyProductionFinder;

    @Test
    public void createWhenSuccessfulShouldReturnModel() {
        when(adapter.save(any(HoneyProduction.class))).thenAnswer(ans -> ans.getArgument(0));
        when(userAccountFinder.findByEmail(anyString())).thenReturn(getUserAccount());

        var honeyProduction = creator.create("test@email.com");
        assertNotNull(honeyProduction);
        assertNotNull(honeyProduction.getHarvestDate());
        assertNotNull(honeyProduction.getAssociationId());
    }

    @Test
    @DisplayName("create When Association Has Honey Production In Progress Should Throw ResourceConflictException")
    public void createWhenAssociationHasHoneyProductionInProgressShouldThrowResourceConflictException() {
        when(honeyProductionFinder.existsHoneyProductionInProgress(anyLong())).thenReturn(true);
        when(userAccountFinder.findByEmail(anyString())).thenReturn(getUserAccount());

        assertThrows(ResourceConflictException.class, () -> creator.create("test@email.com"));
    }

    @Test
    @DisplayName("create When User is not Association member should Throw ResourceNotFoundException")
    public void createWhenUserIsNotAssociationMemberShouldThrowResourceNotFoundException() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(mock(UserAccount.class));
        assertThrows(ResourceNotFoundException.class, () -> creator.create("test@email.com"));
    }

    private UserAccount getUserAccount(){
        var account = new UserAccount();
        account.setAssociation(mock(Association.class));
        return account;
    }
}
