package com.promel.api.usecase.test.authentication;

import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.authentication.UserAuthFinder;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.usecase.exception.ResourceNotFoundException;
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
public class UserAuthFinderTest {
    @InjectMocks
    private UserAuthFinder userAuthFinder;
    @Mock
    private UserAuthAdapter adapter;

    @Test
    public void findByEmailWhenSuccessfulShouldReturnModel() {
        Mockito.when(adapter.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(Mockito.mock(UserAuth.class)));
        assertNotNull(userAuthFinder.findByEmail("email"));
    }

    @Test
    public void findByEmailWhenNotFoundShouldThrowResourceNotFoundException() {
        Mockito.when(adapter.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userAuthFinder.findByEmail("email"));
    }
}