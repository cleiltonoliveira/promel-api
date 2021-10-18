package com.promel.api.usecase.test.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssociationFinderTest {
    @InjectMocks
    private AssociationFinder associationFinder;
    @Mock
    private AssociationAdapter adapter;

    @Test
    public void findByIdWhenSuccessfulShouldReturnModel() {
        when(adapter.findById(anyLong())).thenReturn(Optional.of(mock(Association.class)));
        assertNotNull(associationFinder.findById(1L));
    }

    @Test
    public void findByIdWhenNotFoundShouldThrowResourceNotFoundException() {
        when(adapter.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> associationFinder.findById(0L));
    }

    @Test
    public void findByInviteCodeWhenSuccessfulShouldReturnModel() {
        when(adapter.findByInviteCode(anyString())).thenReturn(Optional.of(mock(Association.class)));
        assertNotNull(associationFinder.findByInviteCode("testInviteCode"));
    }

    @Test
    public void findByInviteCodeWhenNotFoundShouldThrowResourceNotFoundException() {
        when(adapter.findByInviteCode(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> associationFinder.findByInviteCode("testInviteCode"));
    }
}
