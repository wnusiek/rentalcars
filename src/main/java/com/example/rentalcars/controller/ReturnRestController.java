package com.example.rentalcars.controller;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/return")
public class ReturnRestController {
    private final ReturnService returnService;

    @GetMapping
    public List<ReturnModel> get() {
        return returnService.getReturnModelList();
    }
}
