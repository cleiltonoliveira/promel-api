package com.promel.api.usecase.test.user;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.user.UserAccountFinder;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserAccountFinderTest {
    @InjectMocks
    private UserAccountFinder userAccountFinder;
    @Mock
    private UserAccountAdapter adapter;

    @Test
    public void findByIdWhenSuccessfulShouldReturnModel() {
        Mockito.when(adapter.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Mockito.mock(UserAccount.class)));
        assertNotNull(userAccountFinder.findById(1L));
    }

    @Test
    public void findByIdWhenNotFoundShouldThrowResourceNotFoundException() {
        Mockito.when(adapter.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userAccountFinder.findById(0L));
    }

    @Test
    public void findByUserAuthWhenSuccessfulShouldReturnModel() {
        Mockito.when(adapter.findByUserAuth(ArgumentMatchers.any())).thenReturn(Optional.of(Mockito.mock(UserAccount.class)));
        assertNotNull(userAccountFinder.findByUserAuth(Mockito.mock(UserAuth.class)));
    }

    @Test
    public void findByUserAuthWhenNotFoundShouldThrowResourceNotFoundException() {
        Mockito.when(adapter.findByUserAuth(ArgumentMatchers.any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userAccountFinder.findByUserAuth(Mockito.mock(UserAuth.class)));
    }
}
