package com.promel.api.persistence.test.authentication;

import com.promel.api.persistence.authentication.UserAuthEntity;
import com.promel.api.persistence.authentication.UserAuthRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ContextConfiguration(classes = {UserAuthRepository.class})
@EnableJpaRepositories(basePackages = {"com.promel.api.persistence.*"})
@EntityScan("com.promel.api.persistence.*")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAuthRepositoryTest {

    @Autowired
    private UserAuthRepository repository;

    @BeforeAll
    public void setup() {
        var entity = new UserAuthEntity(null, "123", "gandalf@ohobbit.com", null);
        repository.save(entity);
    }

    @Test
    public void findByIdShouldReturnEntity() {
        assertNotNull(repository.findById(1L).orElse(null));
    }

    @Test
    public void findByIdWhenNoMatchShouldReturnEmpty() {
        assertEquals(Optional.empty(), repository.findById(0L));
    }

    @Test
    public void findByEmailShouldReturnEntity() {
        assertNotNull(repository.findByEmail("gandalf@ohobbit.com").orElse(null));
    }

    @Test
    public void findByEmailWhenNoMatchShouldReturnEmpty() {
        assertEquals(Optional.empty(), repository.findByEmail("email@email.com"));
    }
}
