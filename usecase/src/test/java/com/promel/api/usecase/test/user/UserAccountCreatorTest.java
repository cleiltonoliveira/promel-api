package com.promel.api.usecase.test.user;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.role.RoleFinder;
import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
public class UserAccountCreatorTest {
    @InjectMocks
    private UserAccountCreator userAccountCreator;
    @Mock
    private UserAccountAdapter userAccountAdapter;
    @Mock
    private UserAuthAdapter userAuthAdapter;
    @Mock
    private RoleFinder roleFinder;

    @Test
    public void createWhenSuccessShouldReturnModel() {
        var model = buildModel();
        Mockito.when(userAccountAdapter.save(ArgumentMatchers.any(UserAccount.class))).then(ans -> ans.getArgument(0));

        var userAccount = userAccountCreator.create(model);

        assertNotNull(userAccount);
        assertNotNull(userAccount.getCreationDate());
        assertNotNull(userAccount.getUserAuth().getRoles());
        assertEquals(model.getName(), userAccount.getName());
        assertEquals(model.getUserAuth().getEmail(), userAccount.getUserAuth().getEmail());
    }

    @Test
    public void createWhenEmailAlreadyExistsShouldThrowResourceConflictException() {
        var model = buildModel();
        Mockito.when(userAuthAdapter.existsByEmail(ArgumentMatchers.anyString())).thenReturn(true);
        assertThrows(ResourceConflictException.class, () -> userAccountCreator.create(model));
    }

    private UserAccount buildModel() {
        var account = new UserAccount();
        account.setName("Thorin");

        var userAuth = new UserAuth();
        userAuth.setEmail("thorin@ohobbit.com");
        userAuth.setPassword("Oakenshield");

        account.setUserAuth(userAuth);
        return account;
    }
}
