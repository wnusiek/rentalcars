package com.example.rentalcars.controller;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}

