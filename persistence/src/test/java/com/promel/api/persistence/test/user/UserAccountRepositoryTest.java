package com.promel.api.persistence.test.user;

import com.promel.api.persistence.authentication.UserAuthEntity;
import com.promel.api.persistence.user.UserAccountEntity;
import com.promel.api.persistence.user.UserAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ContextConfiguration(classes = {UserAccountRepository.class})
@EnableJpaRepositories(basePackages = {"com.promel.api.persistence.*"})
@EntityScan("com.promel.api.persistence.*")
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createShouldPersistData() {
        var entity = buildEntity();
        repository.save(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getUserAuth().getId());
        assertEquals("Frodo", entity.getName());
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setName(null);

        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    @Test
    public void createWhenUserAuthIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setUserAuth(null);

        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    @Test
    public void deleteShouldRemoveData() {
        var entity = buildEntity();
        repository.save(entity);
        assertNotNull(entity.getId());

        repository.delete(entity);
        Assertions.assertEquals(Optional.empty(), repository.findById(entity.getId()));
    }


    @Test
    public void UpdateShouldChangeAndPersistData() {
        var entity = buildEntity();

        var id = repository.save(entity).getId();
        assertNotNull(entity.getId());

        entity.setName("Thorin");
        entity.getUserAuth().setEmail("thorin@gmail.com");
        repository.save(entity);

        Assertions.assertEquals("Thorin", repository.findById(id).get().getName());
        Assertions.assertEquals("thorin@gmail.com", repository.findById(id).get().getUserAuth().getEmail());
    }

    @Test
    public void findByUserAuthShouldReturnEntity() {
        var entity = buildEntity();
        repository.save(entity);

        Assertions.assertEquals(entity, repository.findByUserAuth(entity.getUserAuth()).get());
    }

    private UserAccountEntity buildEntity() {
        var entity = new UserAccountEntity();
        entity.setName("Frodo");
        entity.setUserAuth(new UserAuthEntity(null, "pass", "frodo@email.com", null));
        entity.setCreationDate(LocalDateTime.now());
        return entity;
    }
}
