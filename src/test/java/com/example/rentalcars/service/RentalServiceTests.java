package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.RentalAdditionException;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTests {
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private ReturnService returnService;
    @InjectMocks
    private RentalService rentalService;
    private RentalModel rentalModel0;
    @BeforeEach
    public void setup() {
        rentalModel0 = new RentalModel(
                1L,
                new EmployeeModel(),
                LocalDate.of(2024, 5, 1),
                new ReservationModel(),
                "Jakiś komentarz");
    }

    @Test
    public void testAddRental_ReturnRental() {
        when(rentalRepository.save(rentalModel0)).thenReturn(rentalModel0);
        var savedRental = rentalService.addRental(rentalModel0);
        assertThat(savedRental).isNotNull();
    }

    @Test
    public void testAddRental_ExceptionThrown() {
        when(rentalRepository.save(rentalModel0)).thenThrow(new RuntimeException());
        assertThrows(RentalAdditionException.class, () -> rentalService.addRental(rentalModel0));
    }

    @Test
    public void testGetRentalList() {
        when(rentalRepository.findAll()).thenReturn(List.of(rentalModel0, new RentalModel(), new RentalModel()));
        List<RentalModel> rentalModelList = rentalService.getRentalList();
        assertThat(rentalModelList.size()).isEqualTo(3);
    }

    @Test
    public void testGetRentalList_EmptyList() {
        when(rentalRepository.findAll()).thenReturn(List.of());
        List<RentalModel> rentalModelList = rentalService.getRentalList();
        assertThat(rentalModelList).isEmpty();
    }

    @Test
    public void testFindById_RentalFound() {
        Long rentalId = 1L;
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rentalModel0));
        var savedRental = rentalService.findById(rentalId);
        assertThat(savedRental).isNotNull();
    }

    @Test
    public void testFindById_ExceptionThrown() {
        Long rentalId = 1L;
        when(rentalRepository.findById(rentalId)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> rentalService.findById(rentalId));
    }

    @Test
    public void testUpdateRental_Success() {
        when(rentalRepository.save(rentalModel0)).thenReturn(rentalModel0);
        rentalService.updateRental(rentalModel0);
        verify(rentalRepository, times(1)).save(rentalModel0);
    }

    @Test
    public void testRemoveRental() {
        Long rentalId = 1L;
        willDoNothing().given(rentalRepository).deleteById(rentalId);
        rentalService.removeRental(rentalId);
        verify(rentalRepository, times(1)).deleteById(rentalId);
    }

    @Test
    public void testFindByReservation_RentalReturned() {
        ReservationModel reservationModel1 = new ReservationModel();
        RentalModel rentalModel1 = new RentalModel();
        rentalModel1.setReservation(reservationModel1);
        when(rentalRepository.findByReservation(reservationModel1)).thenReturn(Optional.of(rentalModel1));
        var savedRental = rentalService.findByReservation(reservationModel1);
        assertThat(savedRental).isEqualTo(rentalModel1);
    }

    @Test
    public void testFindByReservation_ExceptionThrown() {
        ReservationModel reservationModel1 = new ReservationModel();
        RentalModel rentalModel1 = new RentalModel();
        rentalModel1.setReservation(reservationModel1);
        when(rentalRepository.findByReservation(reservationModel1)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> rentalService.findByReservation(reservationModel1));
    }

    @Test
    public void testGetRentalListWithFilters_NoCriteria_NoEmptyResult() {
        when(rentalRepository.findWithFilters(null, null, null, null)).thenReturn(List.of(new RentalModel(), new RentalModel()));
        var savedRentals = rentalService.getRentalListWithFilters(null, null, null, null);
        assertThat(savedRentals.size()).isEqualTo(2);
    }

    @Test
    public void testGetRentalListOfNotReturnedCarsByReturnDepartment_NullDepartment() {
        ReservationModel reservationModel1 = new ReservationModel();
        reservationModel1.setReservationStatus(ReservationStatus.RENTED);
        rentalModel0.setReservation(reservationModel1);
        when(returnService.getReturnModelList()).thenReturn(List.of());
        when(rentalService.getRentalListOfNotReturnedCarsAllDepartments()).thenReturn(List.of(rentalModel0));
        var savedRentals = rentalService.getRentalListOfNotReturnedCarsByReturnDepartment(null);
        assertThat(savedRentals).containsExactly(rentalModel0);
    }

    @Test
    public void testGetRentalListOfNotReturnedCarsByReturnDepartment_SpecificDepartmentNoReservationsEmptyListReturned() {
        DepartmentModel departmentModel1 = new DepartmentModel();
        when(rentalRepository.findAll()).thenReturn(List.of());
        var savedRentals = rentalService.getRentalListOfNotReturnedCarsByReturnDepartment(departmentModel1);
        assertThat(savedRentals).isEmpty();
    }

}
