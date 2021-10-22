package com.promel.api.persistence.test.productionunit;

import com.promel.api.persistence.productiounit.ProductionUnitEntity;
import com.promel.api.persistence.productiounit.ProductionUnitRepository;
import com.promel.api.persistence.test.SetupRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = {ProductionUnitRepository.class})
@SetupRepositoryTest
public class ProductionUnitRepositoryTest {

    @Autowired
    private ProductionUnitRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createShouldPersistData() {
        var entity = buildEntity();
        repository.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    public void createWhenUserIdIsNullShouldThrowConstraintViolationException() {
        var entity = buildEntity();
        entity.setUserAccountId(null);

        assertThrows(ConstraintViolationException.class, () -> {
            repository.save(entity);
            entityManager.flush();
        });
    }

    private ProductionUnitEntity buildEntity() {
        var unit = new ProductionUnitEntity();
        unit.setTotalProduction(0.0);
        unit.setLastModificationDate(LocalDateTime.now());
        unit.setUserAccountId(1L);
        return unit;
    }
}
