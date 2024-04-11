package com.example.rentalcars.service;

import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.ReservationRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final RentalService rentalService;
    public void addReservation(ReservationModel reservation) {

        if (beforeAfterDatesValidation(reservation)){
            if (getCarAvailabilityByDateRange(reservation.getCar().getId(), reservation.getDateFrom(), reservation.getDateTo())){
                reservation.setPrice(calculateRentalCost(reservation));
                reservationRepository.save(reservation);
            } else throw new InputMismatchException ("Samochód niedostępny w podanym terminie!");
        } else throw new IllegalArgumentException ("Nieprawidłowa kolejność dat!");
    }

    public List<ReservationModel> getReservationList() {
        return reservationRepository.findAll();
    }

//    public List<ReservationDto> getReservationList() {
//        return reservationRepository.findAll().stream().map(r -> new ReservationDto(r.getCar(),r.getDateFrom(),r.getDateTo(),r.getPrice(),r.getReceptionVenue(),r.getReturnVenue(),r.getCustomer())).toList();
//    }

    public List<ReservationModel> getReservationListOfNotRentedCars(){
        List<Long> rentedCarsReservationsIds = rentalService.getRentalList()
                .stream()
                .map(rentalModel -> rentalModel.getReservation().getId())
                .toList();

        return getReservationList()
                .stream()
                .filter(reservationModel -> reservationModel.getReservationStatus().equals(ReservationStatus.RESERVED))
                .filter(reservationModel -> !rentedCarsReservationsIds.contains(reservationModel.getId()))
                .toList();
    }

    public List<ReservationModel> getReservationListByCustomerLastName(String lastName){
        if (lastName == null || lastName.isEmpty()){
            return getReservationList();
        }
        return getReservationList()
                .stream()
                .filter(reservation -> reservation.getCustomer().getLastName().equals(lastName))
                .toList();
    }

    public void editReservation(ReservationModel editReservation) {
        reservationRepository.save(editReservation);
    }

    public void removeReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public void removeReservation(ReservationModel reservationModel){
        reservationRepository.deleteById(reservationModel.getId());
    }

    public Optional<ReservationModel> findById(Long id) {
        return reservationRepository.findById(id);
    }

    private List<ReservationModel> getReservationListByCarId(Long carId) {
        return getReservationList().stream()
                .filter(r -> r.getCar().getId().equals(carId))
                .collect(Collectors.toList());
    }

    public Boolean getCarAvailabilityByDateRange(Long carId, LocalDate dateFrom, LocalDate dateTo) {
        var reservationList = getReservationListByCarId(carId);
        for (ReservationModel r : reservationList) {
            if (dateFrom.equals(r.getDateFrom()) || dateFrom.equals(r.getDateTo()) || dateTo.equals(r.getDateFrom()) || dateTo.equals(r.getDateTo())){
                return false;
            }
            else if ((dateFrom.isAfter(r.getDateFrom()) && dateFrom.isBefore(r.getDateTo())) || (dateTo.isBefore(r.getDateTo()) && dateTo.isAfter(r.getDateFrom()))
            || (dateFrom.isBefore(r.getDateFrom()) && dateTo.isAfter(r.getDateTo()))) {
                return false;
            }
            if (r.getReservationStatus().equals(ReservationStatus.RESERVED)) {
                return false;
            }
        }
        return true;
    }

    public List<CarModel> getAvailableCarsByDateRange(List<CarModel> cars, LocalDate dateFrom, LocalDate dateTo) {
        List<CarModel> availableInDateRangeCarList = new ArrayList<>();

        for (CarModel c : cars) {
            if (getCarAvailabilityByDateRange(c.getId(), dateFrom, dateTo)) {
                availableInDateRangeCarList.add(c);
            }
        }
        return availableInDateRangeCarList;
    }

    public Boolean beforeAfterDatesValidation(ReservationModel reservation) {
        if (reservation.getDateFrom().isBefore(reservation.getDateTo()) || reservation.getDateFrom().equals(reservation.getDateTo())){
            return true;
        }
        return false;
    }

    public  BigDecimal calculateRentalCost(ReservationModel reservation) {
//        BigDecimal dailyRentalPrice =  carRepository.findById(reservation.getCar().getId()).get().getPrice();
        BigDecimal dailyRentalPrice = reservation.getCar().getPrice();
        LocalDate startDate = reservation.getDateFrom();
        LocalDate endDate = reservation.getDateTo();

        // Obliczamy różnicę między datami i zaokrąglamy w górę do pełnych dni
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // Obliczamy koszt rezerwacji (cena za dzień * liczba dni)
        BigDecimal totalCost = dailyRentalPrice.multiply(BigDecimal.valueOf(numberOfDays));

        if (reservation.getReceptionVenue().equals(reservation.getReturnVenue())){
            return totalCost;
        } else {
            return totalCost.add(BigDecimal.valueOf(100l));
        }
    }

    public List<ReservationModel> getReservationListLoggedUser(){
        String loggedUserName = userService.getNameOfLoggedUser();
        return getReservationList().stream().filter(reservation -> reservation.getCustomer().getUser().getName().equals(loggedUserName)).toList();
    }

    public void cancelReservation(ReservationModel reservationModel){
        LocalDate reservationStartDate = reservationModel.getDateFrom();
        LocalDate today = LocalDate.now();
        Long difference = ChronoUnit.DAYS.between(today, reservationStartDate);

        if (today.isBefore(reservationStartDate) && difference >= 2) {
            if (reservationModel.getReservationStatus().equals(ReservationStatus.RESERVED)) {
                Notification.show("Rezerwacja anulowana bez opłat").setPosition(Notification.Position.MIDDLE);
                reservationModel.setReservationStatus(ReservationStatus.CANCELLED);
                reservationModel.setPrice(BigDecimal.valueOf(0));
                editReservation(reservationModel);
            }
        }
        else if (difference < 2 && difference >= 0){
            if (reservationModel.getReservationStatus().equals(ReservationStatus.RESERVED)){
                Notification.show("Zostanie pobrana opłata 20% ceny rezerwacji").setPosition(Notification.Position.MIDDLE);
                reservationModel.setReservationStatus(ReservationStatus.CANCELLED);
                BigDecimal handlingFee = reservationModel.getPrice().multiply(BigDecimal.valueOf(0.2));
                reservationModel.setPrice(handlingFee);
                editReservation(reservationModel);
            } else {
                Notification.show("Ta rezerwacja została już anulowana").setPosition(Notification.Position.MIDDLE);
            }
        } else {
            Notification.show("Nie można anulować tej rezerwacji").setPosition(Notification.Position.MIDDLE);
        }
    }

    public void setReservationStatus(Long id, ReservationStatus reservationStatus) {
        var reservation = findById(id);
        if (reservation.isPresent()){
            var r = reservation.get();
            r.setReservationStatus(reservationStatus);
            editReservation(r);
        }
    }
}
