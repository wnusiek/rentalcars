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
import static org.mockito.BDDMockito.given;
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
    private RentalModel rentalModel1;
    private RentalModel rentalModel2;
    private ReservationModel reservationModel1;
    private DepartmentModel departmentModel1;
    private Long rentalId1;
    @BeforeEach
    public void setup() {
        rentalId1 = 1L;
        reservationModel1 = new ReservationModel();
        reservationModel1.setReservationStatus(ReservationStatus.RENTED);
        rentalModel1 = new RentalModel(
                rentalId1,
                new EmployeeModel(),
                LocalDate.of(2024, 5, 1),
                reservationModel1,
                "Jakiś komentarz");
        rentalModel2 = new RentalModel();
        rentalModel2.setReservation(reservationModel1);
        departmentModel1 = new DepartmentModel();
    }

    @Test
    public void testAddRental_ReturnRental() {
        given(rentalRepository.save(rentalModel1)).willReturn(rentalModel1);
        var savedRental = rentalService.addRental(rentalModel1);
        assertThat(savedRental).isNotNull();
    }

    @Test
    public void testAddRental_ExceptionThrown() {
        given(rentalRepository.save(rentalModel1)).willThrow(new RuntimeException());
        assertThrows(RentalAdditionException.class, () -> rentalService.addRental(rentalModel1));
    }

    @Test
    public void testGetRentalList_ReturnList() {
        given(rentalRepository.findAll()).willReturn(List.of(rentalModel1, rentalModel2, new RentalModel()));
        var rentalModelList = rentalService.getRentalList();
        assertThat(rentalModelList.size()).isEqualTo(3);
    }

    @Test
    public void testGetRentalList_ReturnEmptyList() {
        given(rentalRepository.findAll()).willReturn(List.of());
        var rentalModelList = rentalService.getRentalList();
        assertThat(rentalModelList).isEmpty();
    }

    @Test
    public void testFindById_ReturnRental() {
        given(rentalRepository.findById(rentalId1)).willReturn(Optional.of(rentalModel1));
        var savedRental = rentalService.findById(rentalId1);
        assertThat(savedRental).isNotNull();
    }

    @Test
    public void testFindById_ExceptionThrown() {
        given(rentalRepository.findById(rentalId1)).willThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> rentalService.findById(rentalId1));
    }

    @Test
    public void testUpdateRental_Success() {
        given(rentalRepository.save(rentalModel1)).willReturn(rentalModel1);
        rentalService.updateRental(rentalModel1);
        verify(rentalRepository, times(1)).save(rentalModel1);
    }

    @Test
    public void testRemoveRental_Success() {
        willDoNothing().given(rentalRepository).deleteById(rentalId1);
        rentalService.removeRental(rentalId1);
        verify(rentalRepository, times(1)).deleteById(rentalId1);
    }

    @Test
    public void testFindByReservation_ReturnRental() {
        given(rentalRepository.findByReservation(reservationModel1))
                .willReturn(Optional.of(rentalModel2));
        var savedRental = rentalService.findByReservation(reservationModel1);
        assertThat(savedRental).isEqualTo(rentalModel2);
    }

    @Test
    public void testFindByReservation_ExceptionThrown() {
        given(rentalRepository.findByReservation(reservationModel1))
                .willThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> rentalService.findByReservation(reservationModel1));
    }

    @Test
    public void testGetRentalListWithFilters_NoCriteria_NoEmptyResult() {
        given(rentalRepository.findWithFilters(null, null, null, null))
                .willReturn(List.of(new RentalModel(), new RentalModel()));
        var savedRentals = rentalService.getRentalListWithFilters(null, null, null, null);
        assertThat(savedRentals.size()).isEqualTo(2);
    }

    @Test
    public void testGetRentalListOfNotReturnedCarsByReturnDepartment_NullDepartmentReturnAllRentals() {
        given(returnService.getReturnModelList()).willReturn(List.of());
        given(rentalService.getRentalListOfNotReturnedCarsAllDepartments()).willReturn(List.of(rentalModel1));
        var savedRentals = rentalService.getRentalListOfNotReturnedCarsByReturnDepartment(null);
        assertThat(savedRentals).containsExactly(rentalModel1);
    }

    @Test
    public void testGetRentalListOfNotReturnedCarsByReturnDepartment_SpecificDepartmentNoReservationsEmptyListReturned() {
        given(rentalRepository.findAll()).willReturn(List.of());
        var savedRentals = rentalService.getRentalListOfNotReturnedCarsByReturnDepartment(departmentModel1);
        assertThat(savedRentals).isEmpty();
    }

}
