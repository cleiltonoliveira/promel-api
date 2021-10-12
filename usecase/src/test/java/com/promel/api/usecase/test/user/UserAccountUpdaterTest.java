package com.promel.api.usecase.test.user;

import com.promel.api.domain.model.Association;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.authentication.UserAuthUpdater;
import com.promel.api.usecase.user.UserAccountFinder;
import com.promel.api.usecase.user.UserAccountUpdater;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

        Mockito.when(userAccountFinder.findByUserAuth(ArgumentMatchers.any())).thenReturn(model);
        Mockito.when(userAccountAdapter.save(ArgumentMatchers.any(UserAccount.class))).then(ans -> ans.getArgument(0));
        Mockito.when(associationFinder.findById(ArgumentMatchers.anyLong())).then(ans -> {
            var association = new Association();
            association.setId(ans.getArgument(0));
            return association;
        });

        var associationId = 1L;

        var account = userAccountUpdater.linkUserToAssociation(associationId, "aragorn@osa.com");

        Assertions.assertNotNull(account);
        Assertions.assertEquals(associationId, account.getAssociation().getId());
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
