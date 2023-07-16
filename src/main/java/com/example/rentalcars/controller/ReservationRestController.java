package com.example.rentalcars.controller;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public List<ReservationModel> get(){
        return reservationService.getReservationList();
    }

    @PostMapping("/addReservation")
    public void add(ReservationModel reservation){
        reservationService.addReservation(reservation);
    }
}

