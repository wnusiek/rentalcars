package com.example.rentalcars.service;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.repository.ReturnRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final DepartmentService departmentService;
    private final CarService carService;

    public void addReturn(ReturnModel returnModel) {
        if(!returnModel.getDateOfReturn().isEqual(LocalDate.now())){
            Notification.show("Coś napierdaczyłeś z datą drogi kolego");
            return;
        }
        returnModel.setTotalCost(returnModel.getReservation().getPrice());
        departmentService.addCarToDepartment(returnModel.getReservation().getCar().getId(), returnModel.getReservation().getReturnVenue().getId());
        carService.setCarStatus(returnModel.getReservation().getCar().getId(), CarStatus.AVAILABLE);
        returnRepository.save(returnModel);
    }

    public void addReturn(ReturnModel returnModel, BigDecimal supplement) {
        returnModel.setSupplement(supplement);
        returnModel.setTotalCost(returnModel.getReservation().getPrice().add(returnModel.getSupplement()));
        departmentService.addCarToDepartment(returnModel.getReservation().getCar().getId(), returnModel.getReservation().getReturnVenue().getId());
        carService.setCarStatus(returnModel.getReservation().getCar().getId(), CarStatus.AVAILABLE);
        returnRepository.save(returnModel);
    }

    public List<ReturnModel> getReturnModelList() {
        return returnRepository.findAll();
    }

    public void updateReturn(ReturnModel returnModel) {
        returnRepository.save(returnModel);
    }

    public void deleteReturn(Long id) {
        returnRepository.deleteById(id);
    }

    public ReturnModel findById(Long id) {
        return returnRepository.findById(id).orElse(null);
    }

    public BigDecimal getIncome (List<ReturnModel> returnModelList) {
        BigDecimal income = null;
        returnModelList.stream().forEach(e -> income.add(e.getTotalCost()));
        return income;
    }


}
