package com.promel.api.persistence.test.authentication;

import com.promel.api.persistence.authentication.UserAuthEntity;
import com.promel.api.persistence.authentication.UserAuthRepository;
import com.promel.api.persistence.test.SetupRepositoryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {UserAuthRepository.class})
@SetupRepositoryTest
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
