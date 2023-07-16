package com.example.rentalcars.service;

import com.example.rentalcars.model.CarRentalModel;
import com.example.rentalcars.repository.CarRentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CarRentalService {

    private final CarRentalRepository carRentalRepository;


    public void postAddCarRental(CarRentalModel carRentalModel) {
        carRentalRepository.save(carRentalModel);
    }
    public List<CarRentalModel> getCarRentalModelList() {
        return carRentalRepository.findAll();
    }

    public CarRentalModel findById(Long id) {
        return carRentalRepository.findById(id).orElse(null);
    }

    public void updateCarRental(CarRentalModel carRentalModel) {
        carRentalRepository.save(carRentalModel);
    }

    public void removeCarRental(Long id) {
        carRentalRepository.deleteById(id);
    }
}
