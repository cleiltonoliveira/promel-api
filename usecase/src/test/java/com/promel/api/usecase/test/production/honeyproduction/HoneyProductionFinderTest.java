package com.promel.api.usecase.test.production.honeyproduction;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionFinder;
import com.promel.api.usecase.production.honeyproduction.adapter.HoneyProductionAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HoneyProductionFinderTest {
    @InjectMocks
    private HoneyProductionFinder finder;
    @Mock
    private HoneyProductionAdapter adapter;

    @Test
    public void findByIdWhenSuccessfulShouldReturnModel() {
        when(adapter.findById(anyLong())).thenReturn(Optional.of(mock(HoneyProduction.class)));
        assertNotNull(finder.findById(1L));
    }

    @Test
    @DisplayName("findById when not found should throw ResourceNotFoundException")
    public void findByIdWhenNotFoundShouldThrowResourceNotFoundException() {
        when(adapter.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> finder.findById(0L));
    }

    @Test
    @DisplayName("find all HoneyProduction by association should return list")
    public void findAllHoneyProductionByAssociationShouldReturnList() {
        when(adapter.findAllHoneyProductionByAssociationId(anyLong())).thenReturn(List.of(mock(HoneyProduction.class), mock(HoneyProduction.class)));
        assertNotNull(finder.findAllHoneyProductionByAssociation(1L));
        assertEquals(2, finder.findAllHoneyProductionByAssociation(1L).size());
    }

    @Test
    @DisplayName("find HoneyProduction in progress when successful should return model")
    public void findHoneyProductionInProgressWhenSuccessfulShouldReturnModel() {
        when(adapter.findByAssociationIdAndEndDate(anyLong(), any())).thenReturn(Optional.of(mock(HoneyProduction.class)));
        assertNotNull(finder.findHoneyProductionInProgress(1L));
    }

    @Test
    @DisplayName("find HoneyProduction in progress when not found should throw ResourceNotFoundException")
    public void findHoneyProductionInProgressWhenNotFoundShouldThrowResourceNotFoundException() {
        when(adapter.findByAssociationIdAndEndDate(anyLong(), any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> finder.findHoneyProductionInProgress(1L));
    }
}
