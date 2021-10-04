package com.promel.api.usecase.test.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.AssociationCreator;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.ResourceConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AssociationCreatorTest {

    @InjectMocks
    private AssociationCreator associationCreator;
    @Mock
    private AssociationAdapter adapter;

    @Test
    public void createWhenSuccessfulShouldReturnModel() {
        var model = buildModel();

        Mockito.when(adapter.save(ArgumentMatchers.any(Association.class))).then(ans -> ans.getArgument(0));

        var association = associationCreator.create(model);

        assertNotNull(association);
        assertNotNull(association.getCreationDate());
        assertEquals(model.getName(), association.getName());
        assertEquals(model.getCnpj(), association.getCnpj());
    }

    @Test
    public void createWhenCnpjAlreadyExistsShouldThrowResourceConflictException() {
        Mockito.when(adapter.existsByCnpj(ArgumentMatchers.anyString())).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> associationCreator.create(buildModel()));
    }

    private Association buildModel() {
        var model = new Association();
        model.setName("ADRAP");
        model.setCnpj("02.616.289/0001-01");
        return model;
    }
}
