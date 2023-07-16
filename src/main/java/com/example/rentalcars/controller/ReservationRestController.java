package com.example.rentalcars.controller;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationModel> get(){
        return reservationService.getReservationList();
    }
}
