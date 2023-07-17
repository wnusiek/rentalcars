package com.example.rentalcars.controller;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.service.RentalService;
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
public class RentalController {

    private final RentalService rentalService;

//    @GetMapping("/rental")
//    public String getRentalList(Model model) {
//        List<RentalModel> list = rentalService.getRentalList();
//        model.addAttribute("rentalModel", list);
//        return "rental/rental";
//    }

    /*@GetMapping("/addRental")
    public String getAddRental(){
        return "rental/addRental";
    }

    @PostMapping("/addRental")
    public RedirectView postAddRental(RentalModel rental) {
        rentalService.postAddRental(rental);
        return new RedirectView("/rental");
    }

    @GetMapping("/editRental/{id}")
    public String getEditRental(@PathVariable("id") Long id, Model model) {
        RentalModel rental = rentalService.findById(id);
        model.addAttribute("rentalModel", rental);
        return "rental/editRental";
    }

    @PostMapping("/edit/{id}")
    public RedirectView postEditRental(RentalModel rental){
        rentalService.updateRental(rental);
        return new RedirectView("/rental");
    }

    @PostMapping("/removeRental/{id}")
    public RedirectView removeRental(@PathVariable("id") Long id) {
        rentalService.removeRental(id);
        return new RedirectView("/rental");
    }*/
}
