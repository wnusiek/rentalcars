package com.example.rentalcars.controller;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

//    @GetMapping("/reservation")
//    public String getReservation(Model model){
//        List<ReservationModel> reservationList = reservationService.getReservationList();
//        model.addAttribute("reservationModel",reservationList);
//        return "reservation/reservation";
//    }

//    @GetMapping("/addReservation")
//    public String addReservation(){
//        return "reservation/reservation";
//    }
//
//    @PostMapping("/addReservation")
//        public RedirectView addReservation(ReservationModel reservation){
//            reservationService.addReservation(reservation);
//            return new RedirectView("/reservation");
//    }
//
//    @GetMapping("/editReservation/{id}")
//    public String getEditReservation(@PathVariable("id") Long id, Model model) {
//        ReservationModel reservation = reservationService.findbyId(id);
//        model.addAttribute("reservationModel", reservation);
//        return "reservation/reservation";
//    }
//
//    @PostMapping("/editReservation/{id}")
//    public RedirectView editReservation(ReservationModel editReservation){
//        reservationService.editReservation(editReservation);
//        return new RedirectView("/reservation");
//    }
//
//    @PostMapping("/delReservation/{id}")
//    public RedirectView deleteReservation(@PathVariable("id")Long id){
//        reservationService.removeReservation(id);
//        return new RedirectView("/reservation");
//    }

}
