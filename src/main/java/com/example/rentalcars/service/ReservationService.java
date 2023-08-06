package com.example.rentalcars.service;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.DTO.ReservationDto;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.ReservationRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CarService carService;
    private final UserService userService;
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

    public void editReservation(ReservationModel editReservation) {
        reservationRepository.save(editReservation);
    }

    public void removeReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationModel findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
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
        if (reservation.getDateFrom().isBefore(reservation.getDateTo())){
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

}
