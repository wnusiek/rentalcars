package com.example.rentalcars.controller;

import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReturnRestController {

    private final ReturnService returnService;

    @GetMapping("/return")
    public List<ReturnModel> get() {
        return returnService.getReturnModelList();
    }

    @PostMapping("/addReturn")
    public void add(ReturnModel returnModel, BigDecimal supplement) {
        returnService.addReturn(returnModel, supplement);
    }

    @PostMapping("/editReturn/{id}")
    public void edit(ReturnModel returnModel) {
        returnService.updateReturn(returnModel);
    }

    @PostMapping("removeReturn/{id}")
    public void removeReturn(@PathVariable("id") Long id) {
        returnService.deleteReturn(id);
    }
}