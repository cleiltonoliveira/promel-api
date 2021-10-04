package com.promel.api.usecase.test.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AssociationFinderTest {
    @InjectMocks
    private AssociationFinder associationFinder;
    @Mock
    private AssociationAdapter adapter;

    @Test
    public void findByIdWhenSuccessfulShouldReturnModel() {
        Mockito.when(adapter.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Mockito.mock(Association.class)));
        assertNotNull(associationFinder.findById(1L));
    }

    @Test
    public void findByIdWhenNotFoundShouldThrowResourceNotFoundException() {
        Mockito.when(adapter.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> associationFinder.findById(0L));
    }
}
