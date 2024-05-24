package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.ReservationAdditionException;
import com.example.rentalcars.enums.*;
import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.RentalRepository;
import com.example.rentalcars.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
//    @Mock
//    RentalRepository rentalRepository;
    @Mock
    private RentalService rentalService;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService reservationService;
    private ReservationModel reservationModel0;
    private CarModel carModel;
    @BeforeEach
    public void setup() {
        reservationModel0 = new ReservationModel(1L, new CarModel(), LocalDate.of(2024, Month.JANUARY, 01), LocalDate.of(2024, Month.JANUARY, 03), BigDecimal.valueOf(100), new DepartmentModel(), new DepartmentModel(), new CustomerModel(), ReservationStatus.RESERVED);
    }

    @Test
    public void testAddReservation_ReservationSaved() {
        when(reservationRepository.save(reservationModel0)).thenReturn(reservationModel0);
        ReservationModel savedReservation = reservationService.addReservation(reservationModel0);
        assertThat(savedReservation).isNotNull();
    }

    @Test
    public void testAddReservation_ExceptionThrown() {
        when(reservationRepository.save(reservationModel0)).thenThrow(new RuntimeException());
        assertThrows(ReservationAdditionException.class, () -> reservationService.addReservation(reservationModel0));
    }

    @Test
    public void testGetReservationList_ReturnReservationList() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservationModel0, new ReservationModel(), new ReservationModel()));
        List<ReservationModel> savedReservationList = reservationService.getReservationList();
        assertThat(savedReservationList.size()).isEqualTo(3);
    }

    @Test
    public void testEditReservation() {
        when(reservationRepository.save(reservationModel0)).thenReturn(reservationModel0);
        reservationService.editReservation(reservationModel0);
        verify(reservationRepository, times(1)).save(reservationModel0);
    }

    @Test
    public void testRemoveReservation() {
        Long id = 1L;
        willDoNothing().given(reservationRepository).deleteById(id);
        reservationService.removeReservation(id);
        verify(reservationRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSetReservationStatus_ReservationSaved() {
        Long reservationId = 1L;
        ReservationStatus reservationStatus = ReservationStatus.RENTED;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationModel0));
        reservationService.setReservationStatus(reservationId, reservationStatus);
        assertThat(reservationModel0.getReservationStatus()).isEqualTo(reservationStatus);
        verify(reservationRepository, times(1)).save(reservationModel0);
    }

    @Test
    public void testSetReservationStatus_ExceptionThrown() {
        Long reservationId = 2L;
        ReservationStatus reservationStatus = ReservationStatus.RENTED;
        when(reservationRepository.findById(reservationId)).thenThrow(new RuntimeException());
        assertThrows(EntityNotFoundException.class, () -> reservationService.setReservationStatus(reservationId, reservationStatus));
    }

    @Test
    public void testGetReservationListByCarId_ListReturned() {
        Long carId = 2L;
        CarModel carModel1 = new CarModel(2L, "", "", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.GAS, "", CarStatus.AVAILABLE, "RED", 200, 2010);
        ReservationModel reservationModel1 = new ReservationModel();
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setCar(carModel1);
        reservationModel2.setCar(carModel1);
        when(reservationRepository.findAll()).thenReturn(List.of(reservationModel1, reservationModel2));
        List<ReservationModel> savedReservationList = reservationService.getReservationListByCarId(carId);
        assertThat(savedReservationList.size()).isEqualTo(2);
        assertThat(savedReservationList).contains(reservationModel1);
        assertThat(savedReservationList).contains(reservationModel2);
    }

    @Test
    public void testGetReservationListByCarId_EmptyList() {
        Long carId = 1L;
        CarModel carModel1 = new CarModel(2L, "", "", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.GAS, "", CarStatus.AVAILABLE, "RED", 200, 2010);
        ReservationModel reservationModel1 = new ReservationModel();
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setCar(carModel1);
        reservationModel2.setCar(carModel1);
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        List<ReservationModel> savedReservationList = reservationService.getReservationListByCarId(carId);
        assertThat(savedReservationList).isEmpty();
    }

    @Test
    public void testGetRentedCarsReservationsIds() {
        ReservationModel reservationModel1 = new ReservationModel();
        ReservationModel reservationModel2 = new ReservationModel();
        reservationModel1.setId(1L);
        reservationModel2.setId(2L);

        RentalModel rentalModel1 = new RentalModel();
        RentalModel rentalModel2 = new RentalModel();
        rentalModel1.setReservation(reservationModel1);
        rentalModel2.setReservation(reservationModel2);

        when(rentalService.getRentalList()).thenReturn(List.of(rentalModel1, rentalModel2));

        List<Long> rentedCarsReservationsIds = reservationService.getRentedCarsReservationsIds();

        assertThat(rentedCarsReservationsIds).containsExactlyInAnyOrder(1L, 2L);
    }

//    @Test
//    public void testGetReservationListOfNotRentedCarsAllReceptionDepartments_ListReturned() {
//        ReservationModel reservationModel1 = new ReservationModel();
//        ReservationModel reservationModel2 = new ReservationModel();
//        ReservationModel reservationModel3 = new ReservationModel();
//        reservationModel1.setId(1L);
//        reservationModel2.setId(2L);
//        reservationModel3.setId(3L);
//        reservationModel1.setReservationStatus(ReservationStatus.RESERVED);
//        reservationModel2.setReservationStatus(ReservationStatus.RENTED);
//        reservationModel3.setReservationStatus(ReservationStatus.RESERVED);
//
////        RentalModel rentalModel1 = new RentalModel();
////        rentalModel1.setReservation(reservationModel2);
//        when(rentalService.getRentalList()).thenReturn(List.of());
//        when(reservationService.getRentedCarsReservationsIds()).thenReturn(List.of(2L));
//        when(reservationRepository.findAll()).thenReturn(List.of(reservationModel1, reservationModel2, reservationModel3));
//
//        List<ReservationModel> savedReservationList = reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments();
//
//        assertThat(savedReservationList.size()).isEqualTo(2);
//        assertThat(savedReservationList).containsExactlyInAnyOrder(reservationModel1, reservationModel3);
//    }


}
