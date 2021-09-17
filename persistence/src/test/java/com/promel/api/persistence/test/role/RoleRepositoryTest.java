package com.promel.api.persistence.test.role;

import com.promel.api.persistence.role.RoleEntity;
import com.promel.api.persistence.role.RoleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ContextConfiguration(classes = {RoleRepository.class})
@EnableJpaRepositories(basePackages = {"com.promel.api.persistence.*"})
@EntityScan("com.promel.api.persistence.*")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
