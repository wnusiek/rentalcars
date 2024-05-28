package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.ReservationAdditionException;
import com.example.rentalcars.enums.*;
import com.example.rentalcars.model.*;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
    @Mock
    private RentalService rentalService;
    @Mock
    private UserService userService;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService reservationService;
    private ReservationModel reservationModel1;
    private ReservationModel reservationModel2;
    private ReservationModel reservationModel3;
    private ReservationModel reservationModel4;
    private ReservationModel reservationModel5;

    private RentalModel rentalModel1;
    private RentalModel rentalModel2;
    private DepartmentModel receptionVenue1;
    private DepartmentModel receptionVenue2;
    private DepartmentModel returnVenue1;
    private CarModel carModel1;
    private UserModel userModel1;
    private CustomerModel customerModel1;
    private Long carId1;
    private Long reservationId1;
    private String loggedUserName1;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @BeforeEach
    public void setup() {
        dateFrom = LocalDate.of(2024, 5, 11);
        dateTo = LocalDate.of(2024, 5, 15);

        loggedUserName1 = "Franek";
        userModel1 = new UserModel();
        userModel1.setName(loggedUserName1);
        customerModel1 = new CustomerModel();
        customerModel1.setUser(userModel1);

        carId1 = 1L;
        carModel1 = new CarModel(carId1, "", "", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.GAS, "", CarStatus.AVAILABLE, "RED", 200, 2010);

        receptionVenue1 = new DepartmentModel(1L, "Bytom");
        receptionVenue2 = new DepartmentModel(3L, "Kraków");
        returnVenue1 = new DepartmentModel(2L, "Gdańsk");

        reservationId1 = 1L;
        reservationModel1 = new ReservationModel(reservationId1, carModel1, dateFrom, dateTo, BigDecimal.valueOf(100), receptionVenue1, returnVenue1, customerModel1, ReservationStatus.RESERVED);
        reservationModel2 = new ReservationModel();
        reservationModel3 = new ReservationModel();
        reservationModel4 = new ReservationModel();
        reservationModel5 = new ReservationModel();
        reservationModel2.setId(2L);
        reservationModel3.setId(3L);
        reservationModel4.setId(4L);
        reservationModel5.setId(5L);

        reservationModel2.setDateFrom(LocalDate.of(2024, 5, 16));
        reservationModel2.setDateTo(LocalDate.of(2024, 5, 20));

        reservationModel3.setDateFrom(LocalDate.of(2024, 5, 21));
        reservationModel3.setDateTo(LocalDate.of(2024, 5, 25));

        reservationModel2.setCar(carModel1);
        reservationModel3.setCar(carModel1);

        reservationModel2.setReceptionVenue(receptionVenue1);
        reservationModel3.setReceptionVenue(receptionVenue2);

        reservationModel2.setReservationStatus(ReservationStatus.RESERVED);
        reservationModel3.setReservationStatus(ReservationStatus.RENTED);
        reservationModel4.setReservationStatus(ReservationStatus.RETURNED);
        reservationModel5.setReservationStatus(ReservationStatus.CANCELLED);

        rentalModel1 = new RentalModel();
        rentalModel2 = new RentalModel();
        rentalModel1.setReservation(reservationModel1);
        rentalModel2.setReservation(reservationModel2);
    }

    @Test
    public void testAddReservation_ReservationSaved() {
        given(reservationRepository.save(reservationModel1)).willReturn(reservationModel1);
        var savedReservation = reservationService.addReservation(reservationModel1);
        assertThat(savedReservation).isNotNull();
    }

    @Test
    public void testAddReservation_ExceptionThrown() {
        given(reservationRepository.save(reservationModel1)).willThrow(new RuntimeException());
        assertThrows(ReservationAdditionException.class, () -> reservationService.addReservation(reservationModel1));
    }

    @Test
    public void testGetReservationList_ReturnReservationList() {
        given(reservationRepository.findAll()).willReturn(List.of(reservationModel1, reservationModel2, reservationModel3));
        var savedReservationList = reservationService.getReservationList();
        assertThat(savedReservationList.size()).isEqualTo(3);
    }

    @Test
    public void testGetReservationList_ReturnEmptyList() {
        given(reservationRepository.findAll()).willReturn(List.of());
        var savedReservationList = reservationService.getReservationList();
        assertThat(savedReservationList).isEmpty();
    }

    @Test
    public void testEditReservation_Success() {
        given(reservationRepository.save(reservationModel1)).willReturn(reservationModel1);
        reservationService.editReservation(reservationModel1);
        verify(reservationRepository, times(1)).save(reservationModel1);
    }

    @Test
    public void testRemoveReservation_Success() {
        willDoNothing().given(reservationRepository).deleteById(reservationId1);
        reservationService.removeReservation(reservationId1);
        verify(reservationRepository, times(1)).deleteById(reservationId1);
    }

    @Test
    public void testSetReservationStatus_Success() {
        var reservationStatus = ReservationStatus.RENTED;
        given(reservationRepository.findById(reservationId1)).willReturn(Optional.of(reservationModel1));
        reservationService.setReservationStatus(reservationId1, reservationStatus);
        assertThat(reservationModel1.getReservationStatus()).isEqualTo(reservationStatus);
        verify(reservationRepository, times(1)).save(reservationModel1);
    }

    @Test
    public void testSetReservationStatus_ExceptionThrown() {
        var reservationStatus = ReservationStatus.RENTED;
        given(reservationRepository.findById(reservationId1)).willThrow(new RuntimeException());
        assertThrows(EntityNotFoundException.class, () -> reservationService.setReservationStatus(reservationId1, reservationStatus));
    }

    @Test
    public void testGetReservationListByCarId_ReturnList() {
        given(reservationRepository.findAll()).willReturn(List.of(reservationModel2, reservationModel3));
        var savedReservationList = reservationService.getReservationListByCarId(carId1);
        assertThat(savedReservationList).containsExactlyInAnyOrder(reservationModel2, reservationModel3);
    }

    @Test
    public void testGetReservationListByCarId_ReturnEmptyList() {
        given(reservationRepository.findAll()).willReturn(Collections.emptyList());
        var savedReservationList = reservationService.getReservationListByCarId(carId1);
        assertThat(savedReservationList).isEmpty();
    }

    @Test
    public void testGetRentedCarsReservationsIds_ReturnList() {
        given(rentalService.getRentalList()).willReturn(List.of(rentalModel1, rentalModel2));
        var rentedCarsReservationsIds = reservationService.getRentedCarsReservationsIds();
        assertThat(rentedCarsReservationsIds).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    public void testGetRentedCarsReservationsIds_NoRentalsReturnEmptyList() {
        given(rentalService.getRentalList()).willReturn(List.of());
        var rentedCarsReservationsIds = reservationService.getRentedCarsReservationsIds();
        assertThat(rentedCarsReservationsIds).isEmpty();
    }

    @Test
    public void testGetReservationListOfNotRentedCarsAllReceptionDepartments_NoReservationsReturnEmptyList() {
        given(reservationRepository.findAll()).willReturn(List.of());
        var reservationModelList = reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments();
        assertThat(reservationModelList).isEmpty();
    }

    @Test
    public void testGetReservationListOfNotRentedCarsByReceptionDepartments_NullDepartmentReturnAllReservations() {
        given(reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments()).willReturn(List.of(reservationModel1, reservationModel2));
        var reservationModelList = reservationService.getReservationListOfNotRentedCarsByReceptionDepartment(null);
        assertThat(reservationModelList).containsExactly(reservationModel1, reservationModel2);
    }

    @Test
    public void testGetReservationListOfNotRentedCarsByReceptionDepartments_SpecificDepartmentNoReservationsEmptyListReturned() {
        given(reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments()).willReturn(List.of());
        var reservationModelList = reservationService.getReservationListOfNotRentedCarsByReceptionDepartment(receptionVenue1);
        assertThat(reservationModelList).isEmpty();
    }

    @Test
    public void testGetReservationListOfNotRentedCarsByReceptionDepartments_SpecificDepartmentWithReservationsInDifferentDepartmentEmptyListReturned() {
        given(reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments()).willReturn(List.of());
        var reservationModelList = reservationService.getReservationListOfNotRentedCarsByReceptionDepartment(receptionVenue1);
        assertThat(reservationModelList).isEmpty();
    }

    @Test
    public void testGetReservationListOfNotRentedCarsByReceptionDepartments_SpecificDepartmentReturnList() {
        given(reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments()).willReturn(List.of(reservationModel1, reservationModel2));
        var reservationModelList = reservationService.getReservationListOfNotRentedCarsByReceptionDepartment(receptionVenue1);
        assertThat(reservationModelList).containsExactly(reservationModel1, reservationModel2);
    }

    @Test
    public void testGetReservationListLoggedUser_ReturnList() {
        given(userService.getNameOfLoggedUser()).willReturn(loggedUserName1);
        given(reservationRepository.findAll()).willReturn(List.of(reservationModel1));
        var reservationModelList = reservationService.getReservationListLoggedUser();
        assertThat(reservationModelList).containsExactly(reservationModel1);
    }

    @Test
    public void testGetReservationListLoggedUser_NoReservationsReturnEmptyList() {
        given(userService.getNameOfLoggedUser()).willReturn(loggedUserName1);
        given(reservationRepository.findAll()).willReturn(List.of());
        var reservationModelList = reservationService.getReservationListLoggedUser();
        assertThat(reservationModelList).isEmpty();
    }

    @Test
    public void testGetReservationListLoggedUser_ReservationsOnDifferentUserReturnEmptyList() {
        userModel1.setName("Zdzichu");
        given(userService.getNameOfLoggedUser()).willReturn(loggedUserName1);
        given(reservationRepository.findAll()).willReturn(List.of(reservationModel1));
        var reservationModelList = reservationService.getReservationListLoggedUser();
        assertThat(reservationModelList).isEmpty();
    }

    @Test
    public void testGetReservationListWithFilters_NoCriteria_NoEmptyResult() {
        given(reservationRepository.findWithFilters(null, null, null, null)).willReturn(List.of(reservationModel1, reservationModel2));
        var reservationModelList = reservationService.getReservationListWithFilters(null, null, null, null);
        assertThat(reservationModelList.size()).isEqualTo(2);
    }

    @Test
    public void testCancelOutdatedReservationOfNotRentedCar_OutdatedReservationCancelled() {
        reservationModel1.setDateFrom(LocalDate.now());
        reservationModel2.setDateFrom(LocalDate.now().minusDays(1));
        given(reservationRepository.findAll()).willReturn(List.of(reservationModel1, reservationModel2));
        reservationService.cancelOutdatedReservationOfNotRentedCar();
        verify(reservationRepository, times(1)).save(reservationModel2);
    }

    @Test
    public void testCancelOutdatedReservationOfNotRentedCar_NoOutdatedReservations() {
        reservationModel1.setDateFrom(LocalDate.now().plusDays(1));
        reservationModel2.setDateFrom(LocalDate.now());
        given(reservationRepository.findAll()).willReturn(List.of(reservationModel1, reservationModel2));
        reservationService.cancelOutdatedReservationOfNotRentedCar();
        verify(reservationRepository, times(0)).save(any(ReservationModel.class));
    }

    @Test
    public void testCancelOutdatedReservationOfNotRentedCar_NoReservations() {
        given(reservationRepository.findAll()).willReturn(List.of());
        reservationService.cancelOutdatedReservationOfNotRentedCar();
        verify(reservationRepository, times(0)).save(any(ReservationModel.class));
    }

    @Test
    public void testIsCarAvailableInGivenDateRange_CarAvailable() {
        given(reservationService.getReservationListByCarId(carId1))
                .willReturn(List.of(reservationModel1, reservationModel3));
        var result = reservationService.isCarAvailableInGivenDateRange(
                carId1,
                dateFrom.plusDays(5),
                dateTo.plusDays(5));
        assertThat(result).isTrue();
    }

    @Test
    public void testIsCarAvailableInGivenDateRange_CarUnavailablePreviousReservationLasts() {
        given(reservationService.getReservationListByCarId(carId1))
                .willReturn(List.of(reservationModel1, reservationModel2));
        var result = reservationService.isCarAvailableInGivenDateRange(
                carId1,
                dateFrom.plusDays(9),
                dateTo.plusDays(9));
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarAvailableInGivenDateRange_CarUnavailableNextReservationStarts() {
        given(reservationService.getReservationListByCarId(carId1))
                .willReturn(List.of(reservationModel1, reservationModel2));
        var result = reservationService.isCarAvailableInGivenDateRange(
                carId1,
                dateFrom.minusDays(1),
                dateTo.minusDays(1)
        );
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarAvailableInGivenDateRange_CarUnavailableDatesDuringOtherReservationPeriod() {
        given(reservationService.getReservationListByCarId(carId1)).willReturn(List.of(reservationModel1));
        var result = reservationService.isCarAvailableInGivenDateRange(
                carId1,
                dateFrom.plusDays(1),
                dateTo.minusDays(1));
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarAvailableInGivenDateRange_CarUnavailableEqualsDatesOfOtherReservations() {
        given(reservationService.getReservationListByCarId(carId1))
                .willReturn(List.of(reservationModel1));
        var result = reservationService.isCarAvailableInGivenDateRange(carId1, dateFrom, dateTo);
        assertThat(result).isFalse();
    }

    @Test
    public void testGetAvailableCarsByDateRange_ReturnEmptyList() {
        var carModelList = reservationService.getAvailableCarsByDateRange(new ArrayList<>(), dateFrom, dateTo);
        assertThat(carModelList).isEmpty();
    }

    @Test
    public void testCalculateRentalCost_DifferentVenues() {
        var totalCost = reservationService.calculateRentalCost(reservationModel1);
        assertThat(totalCost).isEqualByComparingTo(new BigDecimal("600"));
    }

    @Test
    public void testCalculateRentalCost_SameVenue() {
        reservationModel1.setReturnVenue(receptionVenue1);
        var totalCost = reservationService.calculateRentalCost(reservationModel1);
        assertThat(totalCost).isEqualByComparingTo(new BigDecimal("500"));
    }

    @Test
    public void testCalculateRentalCost_OneDayRental() {
        reservationModel1.setReturnVenue(receptionVenue1);
        reservationModel1.setDateTo(dateFrom);
        var totalCost = reservationService.calculateRentalCost(reservationModel1);
        assertThat(totalCost).isEqualByComparingTo(new BigDecimal("100"));
    }
}
