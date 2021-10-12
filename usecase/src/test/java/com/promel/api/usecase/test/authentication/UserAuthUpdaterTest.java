package com.promel.api.usecase.test.authentication;

import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.authentication.UserAuthFinder;
import com.promel.api.usecase.authentication.UserAuthUpdater;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.role.RoleFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class UserAuthUpdaterTest {
    @InjectMocks
    private UserAuthUpdater userAuthUpdater;
    @Mock
    private UserAuthFinder userAuthFinder;
    @Mock
    private RoleFinder roleFinder;
    @Mock
    private UserAuthAdapter adapter;

    @Test
    public void updateRolesWhenSuccessfulShouldReturnModel() {
        Mockito.when(userAuthFinder.findByEmail(ArgumentMatchers.anyString())).thenReturn(Mockito.mock(UserAuth.class));
        Mockito.when(adapter.save(ArgumentMatchers.any(UserAuth.class))).thenReturn(Mockito.mock(UserAuth.class));
        assertNotNull(userAuthUpdater.updateRoles("email", Set.of("Role")));
    }

    @Test
    public void updateRolesWhenEmailNotFoundShouldThrowResourceNotFoundException(){
        Mockito.when(userAuthFinder.findByEmail(ArgumentMatchers.anyString())).thenThrow(new ResourceNotFoundException(""));
        assertThrows(ResourceNotFoundException.class , () -> userAuthUpdater.updateRoles("email", Set.of("Role")));
    }

    @Test
    public void updateRolesWhenRolesNotFoundShouldThrowResourceNotFoundException(){
        Mockito.when(roleFinder.findAllByRoleNames(ArgumentMatchers.anySet())).thenThrow(new ResourceNotFoundException(""));
        assertThrows(ResourceNotFoundException.class , () -> userAuthUpdater.updateRoles("email", Set.of("Role")));
    }
}