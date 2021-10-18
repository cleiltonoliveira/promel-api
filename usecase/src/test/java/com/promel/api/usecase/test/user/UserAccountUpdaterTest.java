package com.promel.api.usecase.test.user;

import com.promel.api.domain.model.Association;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.authentication.UserAuthUpdater;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.user.UserAccountFinder;
import com.promel.api.usecase.user.UserAccountUpdater;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAccountUpdaterTest {

    @InjectMocks
    private UserAccountUpdater userAccountUpdater;
    @Mock
    private UserAccountAdapter userAccountAdapter;
    @Mock
    private UserAuthUpdater userAuthUpdater;
    @Mock
    private UserAccountFinder userAccountFinder;
    @Mock
    private AssociationFinder associationFinder;

    @Test
    public void linkUserToAssociationWhenSuccessShouldReturnUpdatedUser() {
        var model = buildModel();

        when(userAccountFinder.findByUserAuth(ArgumentMatchers.any())).thenReturn(model);
        when(userAccountAdapter.save(ArgumentMatchers.any(UserAccount.class))).then(ans -> ans.getArgument(0));
        when(associationFinder.findById(ArgumentMatchers.anyLong())).then(ans -> {
            var association = new Association();
            association.setId(ans.getArgument(0));
            return association;
        });

        var associationId = 1L;

        var account = userAccountUpdater.linkUserToAssociation(associationId, "aragorn@osa.com");

        assertNotNull(account);
        assertEquals(associationId, account.getAssociation().getId());
    }

    @Test
    public void joinAssociationWhenSuccessShouldUpdateUserAssociation() {
        when(userAccountFinder.findByEmail(anyString())).thenReturn(buildModel());
        when(associationFinder.findByInviteCode(anyString())).thenReturn(mock(Association.class));
        when(userAccountAdapter.save(any(UserAccount.class))).then(ans -> ans.getArgument(0));

        assertNotNull(userAccountUpdater.joinAssociation("testInviteCode", "testEmail").getAssociation());
    }

    @Test
    @DisplayName("join association when user is already associated should throw ResourceConflictException")
    public void joinAssociationWhenUserIsAlreadyAssociatedShouldThrowResourceConflictException() {
        when(userAccountFinder.findByEmail(anyString())).then(ans -> {
            var model = buildModel();
            model.setAssociation(mock(Association.class));
            return model;
        });
        when(associationFinder.findByInviteCode(anyString())).thenReturn(mock(Association.class));

        assertThrows(ResourceConflictException.class, () -> userAccountUpdater.joinAssociation("testInviteCode", "testEmail"));
    }

    private UserAccount buildModel() {
        var account = new UserAccount();
        account.setName("Aragorn");

        var userAuth = new UserAuth();
        userAuth.setEmail("aragorn@tolkien.com");
        userAuth.setPassword("pass");

        account.setUserAuth(userAuth);
        return account;
    }
}