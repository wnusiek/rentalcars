package com.example.rentalcars.service;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.repository.RentalRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final DepartmentService departmentService;
    private final CarService carService;

    public void postAddRental(RentalModel rental) {
        if(!rental.getDateOfRental().isEqual(LocalDate.now())){
            Notification.show("Coś napiedaczyłeś z datą drogi kolego");
            return;
        }

        departmentService.removeCarFromDepartment(rental.getReservation().getCar().getId(), rental.getReservation().getReceptionVenue().getId());
        carService.setCarStatus(rental.getReservation().getCar().getId(), CarStatus.HIRED);
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

    public void deleteRental(RentalModel rentalModel){
        rentalRepository.delete(rentalModel);
    }
}
