package com.example.rentalcars.controller;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public List<ReservationModel> get() {
        return reservationService.getReservationList();
    }

    @PostMapping("/addReservation")
    public void add(ReservationModel reservation) {
        reservationService.addReservation(reservation);
    }

    @PostMapping("/editReservation/{id}")
    public void edit(ReservationModel reservation) {
        reservationService.editReservation(reservation);
    }

    @PostMapping("/removeReservation/{id}")
    public void removeReservation(@PathVariable("id") Long id) {
        reservationService.removeReservation(id);
    }

    @GetMapping ("/getCarStatusByDateRange/{id}")
    public CarStatus getCarStatusByDateRange (@PathVariable("id") Long id, LocalDate dateFrom, LocalDate dateTo) {
        if (reservationService.getCarAvailabilityByDateRange(id, dateFrom, dateTo)) {
            return CarStatus.AVAILABLE;
        }
        return CarStatus.UNAVAILABLE;
    }

    @GetMapping ("/getAvailableCarsByDateRange")
    public List<CarModel> getAvailableCarsByDateRange (LocalDate dateFrom, LocalDate dateTo) {
        return reservationService.getAvailableCarsByDateRange(dateFrom, dateTo);
    }

}

