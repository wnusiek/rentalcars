package com.example.rentalcars.controller;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/rental")
public class RentalRestController {
    private final RentalService rentalService;

    @GetMapping("/rental")
    public List<RentalModel> get() {
        return rentalService.getRentalList();
    }

    @PostMapping("/addRental")
    public void add (RentalModel rentalModel) {
        rentalService.postAddRental(rentalModel);
    }

    @PostMapping ("/editRental/{id}")
    public void edit(RentalModel rentalModel) {
        rentalService.updateRental(rentalModel);
    }

    @PostMapping ("removeRental/{id}")
    public void remove (@PathVariable("id") Long id) {
        rentalService.removeRental(id);
    }

}
