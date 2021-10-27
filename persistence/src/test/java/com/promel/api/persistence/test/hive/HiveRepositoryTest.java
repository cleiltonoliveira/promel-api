package com.promel.api.persistence.test.hive;

import com.promel.api.persistence.hive.HiveEntity;
import com.promel.api.persistence.hive.HiveRepository;
import com.promel.api.persistence.test.SetupRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {HiveRepository.class})
@SetupRepositoryTest
public class HiveRepositoryTest {

    @Autowired
    private HiveRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createShouldPersistData() {
        var entity = buildEntity();
        repository.save(entity);
        assertNotNull(entity.getId());
        assertEquals(entity, repository.findById(entity.getId()).orElse(null));
    }

    @Test
    @DisplayName("create When IdentificationCode Is Null Should Throw ConstraintViolationException")
    public void createWhenIdentificationCodeIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setIdentificationCode(null);
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    @Test
    @DisplayName("create When productionUnitId Is Null Should Throw ConstraintViolationException")
    public void createWhenProductionUnitIdIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setProductionUnitId(null);
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    @Test
    @DisplayName("exists By identificationCode and productionUnitId should return true")
    public void existsByIdentificationCodeAndProductionUnitIdShouldReturnTrue() {
        var entity = buildEntity();
        repository.save(entity);
        assertNotNull(entity.getId());
        boolean result = repository.existsByIdentificationCodeAndProductionUnitId(entity.getIdentificationCode(), entity.getProductionUnitId());

        assertTrue(result);
    }

    @Test
    @DisplayName("exists by identificationCode and productionUnitId should return false")
    public void existsByIdentificationCodeAndProductionUnitIdShouldReturnFalse() {
        boolean result = repository.existsByIdentificationCodeAndProductionUnitId("wrongIdCode", 0L);
        assertFalse(result);
    }

    @Test
    @DisplayName("create when creationDate is null should throw ConstraintViolationException")
    public void createWhenCreationDateIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setCreationDate(null);
        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    private HiveEntity buildEntity() {
        var entity = new HiveEntity();
        entity.setIdentificationCode("TestIdCode");
        entity.setProductionUnitId(1L);
        entity.setCreationDate(LocalDateTime.now());
        return entity;
    }
}
