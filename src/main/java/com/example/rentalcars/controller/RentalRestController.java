package com.example.rentalcars.controller;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rental")
public class RentalRestController {
    private final RentalService rentalService;

    @GetMapping
    public List<RentalModel> get() {
        return rentalService.getRentalList();
    }
}
