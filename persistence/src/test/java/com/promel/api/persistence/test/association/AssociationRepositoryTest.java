package com.promel.api.persistence.test.association;


import com.promel.api.persistence.association.AssociationEntity;
import com.promel.api.persistence.association.AssociationRepository;
import com.promel.api.persistence.test.SetupRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AssociationRepository.class})
@SetupRepositoryTest
public class AssociationRepositoryTest {

    @Autowired
    private AssociationRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createShouldPersistData() {
        var entity = buildEntity();
        repository.save(entity);
        assertNotNull(entity.getId());
        assertEquals("ADRAP", entity.getName());
    }

    @Test
    public void createWhenCnpjIsInvalidShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setCnpj("Invalid cnpj");

        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    @Test
    public void createWhenCreationDateIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setCreationDate(null);

        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
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
    public void findByIdShouldReturnEntity() {
        assertNotNull(repository.findById(repository.save(buildEntity()).getId()).orElse(null));
    }

    @Test
    public void findByIdWhenNoMatchShouldReturnEmpty() {
        assertEquals(Optional.empty(), repository.findById(0L));
    }

    @Test
    public void existsByCnpjShouldReturnTrue() {
        repository.save(buildEntity());
        assertTrue(repository.existsByCnpj("02.616.289/0001-01"));
    }

    @Test
    public void existsByCnpjWhenNoMatchShouldReturnFalse() {
        assertFalse(repository.existsByCnpj("000"));
    }

    private AssociationEntity buildEntity() {
        var entity = new AssociationEntity();
        entity.setName("ADRAP");
        entity.setCnpj("02.616.289/0001-01");
        entity.setCreationDate(LocalDateTime.now());
        return entity;
    }
}
