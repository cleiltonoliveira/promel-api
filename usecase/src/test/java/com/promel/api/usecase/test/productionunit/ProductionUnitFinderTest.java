package com.promel.api.usecase.test.productionunit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.productiounit.ProductionUnitFinder;
import com.promel.api.usecase.productiounit.adapter.ProductionUnitAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductionUnitFinderTest {
    @InjectMocks
    private ProductionUnitFinder productionUnitFinder;
    @Mock
    private ProductionUnitAdapter adapter;

    @Test
    public void findByIdWhenSuccessfulShouldReturnModel() {
        when(adapter.findById(anyLong())).thenReturn(Optional.of(mock(ProductionUnit.class)));
        assertNotNull(productionUnitFinder.findById(1L));
    }

    @Test
    public void findByIdWhenNotFoundShouldThrowResourceNotFoundException() {
        when(adapter.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productionUnitFinder.findById(0L));
    }
}
