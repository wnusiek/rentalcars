package com.example.rentalcars.controller;

import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.ReturnService;
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
public class ReturnController {

    private final ReturnService returnService;

    @GetMapping("/return")
    public String getReturn(Model model){
        List<ReturnModel> returnModelList = returnService.getReturnModelList();
        model.addAttribute("returnModel", returnModelList);
        return "return/return";
    }

    @GetMapping("/addReturn")
    public String getAddReturn(){
        return "return/return";
    }

    @PostMapping("/addReturn")
    public RedirectView postAddReturn(ReturnModel returnModel){
        returnService.addReturn(returnModel);
        return new RedirectView("/return");
    }

    @GetMapping("/editReturn/{id}")
    public String getEditReturn(@PathVariable("id") Long id, Model model){
        ReturnModel returnModel = returnService.findById(id);
        model.addAttribute("returnModel", returnModel);
        return "return/return";
    }

    @PostMapping("/editReturn/{id}")
    public RedirectView postEditReturn(ReturnModel returnModel){
        returnService.updateReturn(returnModel);
        return new RedirectView("/return");
    }

    @PostMapping("/removeReturn/{id}")
    public RedirectView removeReturn(@PathVariable("id")Long id){
        returnService.deleteReturn(id);
        return new RedirectView("/return");
    }
}
