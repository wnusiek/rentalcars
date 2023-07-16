package com.example.rentalcars.service;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    public void postAddRental(RentalModel rental) {
        rentalRepository.save(rental);
    }

    public List<RentalModel> getRentalList() {
        return rentalRepository.findAll();
    }

    public RentalModel findById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public void updateRental(RentalModel rental) {
        rentalRepository.save(rental);
    }

    public void removeRental(Long id) {
        rentalRepository.deleteById(id);
    }
}
