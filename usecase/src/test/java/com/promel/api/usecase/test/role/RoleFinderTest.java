package com.promel.api.usecase.test.role;

import com.promel.api.domain.model.Role;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.role.RoleFinder;
import com.promel.api.usecase.role.adapter.RoleAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoleFinderTest {

    @InjectMocks
    private RoleFinder roleFinder;
    @Mock
    private RoleAdapter adapter;

    @Test
    public void findAllByRoleNamesWhenSuccessfulShouldReturnRoleList() {
        var roleNames = Set.of("ADMIN", "USER");

        Mockito.when(adapter.findAllByNames(ArgumentMatchers.anySet()))
                .then(ans ->
                        roleNames.stream().map(name -> {
                            var role = new Role();
                            role.setRole(name);
                            return role;
                        }).collect(Collectors.toList()));

        var roles = roleFinder.findAllByRoleNames(roleNames);

        assertNotNull(roles);
        assertEquals(roleNames.size(), roles.size());
        roles.forEach(role -> assertTrue(roleNames.contains(role.getRole())));
    }

    @Test
    public void findAllByRoleNamesWhenAnyRoleNotFoundShouldThrowResourceNotFoundException() {
        var roleNames = Set.of("ADMIN", "USER");
        Mockito.when(adapter.findAllByNames(ArgumentMatchers.anySet())).thenReturn(List.of());
        assertThrows(ResourceNotFoundException.class, () -> roleFinder.findAllByRoleNames(roleNames));
    }
}
