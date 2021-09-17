package com.promel.api.persistence.test.role;

import com.promel.api.persistence.role.RoleEntity;
import com.promel.api.persistence.role.RoleRepository;
import com.promel.api.persistence.test.SetupRepositoryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {RoleRepository.class})
@SetupRepositoryTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @BeforeAll
    public void setup() {
        var entity = new RoleEntity();
        entity.setRole("ASSOCIATION_USER");
        repository.save(entity);

        entity = new RoleEntity();
        entity.setRole("ASSOCIATION_ADMIN");
        repository.save(entity);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    public void findRoleByNameShouldReturnEntity() {
        var role = "ASSOCIATION_ADMIN";
        assertNotNull(repository.findOneByRole(role).orElse(null));
    }

    @Test
    public void findRoleByNameWhenRoleNotExistsShouldReturnEmpty() {
        var role = " ";
        assertEquals(Optional.empty(), repository.findOneByRole(role));
    }

    @Test
    public void findAllByNamesShouldReturnFoundRoles() {
        assertEquals(2, repository.findAllByRoleIn(new HashSet<>(List.of("ASSOCIATION_ADMIN", "ASSOCIATION_USER"))).size());
    }

    @Test
    public void findAllByNamesWhenNoMatchShouldReturnEmptyList() {
        assertEquals(0, repository.findAllByRoleIn(new HashSet<>(List.of("ROLE1", "ROLE2"))).size());
    }
}
