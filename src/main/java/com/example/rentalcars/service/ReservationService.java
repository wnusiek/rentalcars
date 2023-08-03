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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CarService carService;
    public void addReservation(ReservationModel reservation) {
        if(!getCarAvailabilityByDateRange(reservation.getCar().getId(), reservation.getDateFrom(),reservation.getDateTo())) {
            throw new RuntimeException("Samochód jest zarezerwowany na ten termin");
        }else if(reservation.getDateFrom().isAfter(reservation.getDateTo())){
            Notification.show("Coś napierdoczyłeś z datami drogi przyjacielu. Data DO nie może być wcześniej niż data OD!");
            throw new RuntimeException("Data od jest później niż data do");


        }else {
            reservation.setPrice(calculateRentalCost(reservation));
            reservationRepository.save(reservation);
        }
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
        List<CarModel> availableCarList = new ArrayList<>();

        for (CarModel c : cars) {
            if (getCarAvailabilityByDateRange(c.getId(), dateFrom, dateTo)) {
                availableCarList.add(c);
            }
        }
        return availableCarList;
    }

    public  BigDecimal calculateRentalCost(ReservationModel reservation) {
//        BigDecimal dailyRentalPrice =  carRepository.findById(reservation.getCar().getId()).get().getPrice();
        BigDecimal dailyRentalPrice = reservation.getCar().getPrice();
        LocalDate startDate = reservation.getDateFrom();
        LocalDate endDate = reservation.getDateTo();

        // Upewniamy się, że data końcowa nie jest wcześniejsza niż data początkowa
        // Walidację daty to raczej w w samym rezerwowaniu a nie przy obliczaniu kosztów
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Data końcowa nie może być wcześniejsza niż data początkowa.");
        }

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


}
