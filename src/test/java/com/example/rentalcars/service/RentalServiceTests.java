package com.example.rentalcars.service;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTests {
    @Mock
    private RentalRepository rentalRepository;
    @InjectMocks
    private RentalService rentalService;
    private RentalModel rentalModel0;
    public void setup() {

    }

    @Test
    public void testGetRentalList() {
        when(rentalRepository.findAll()).thenReturn(List.of(new RentalModel(), new RentalModel()));

        List<RentalModel> rentalModelList = rentalService.getRentalList();

        assertThat(rentalModelList.size()).isEqualTo(2);
    }

    @Test
    public void testGetRentalList_EmptyList() {
        when(rentalRepository.findAll()).thenReturn(List.of());

        List<RentalModel> rentalModelList = rentalService.getRentalList();

        assertThat(rentalModelList).isEmpty();
    }
}
