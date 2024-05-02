package com.example.rentalcars.service;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.RentalRepository;
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
    private final ReturnService returnService;

    public void addRental(RentalModel rental) {
        var reservation = rental.getReservation();
        var carId = reservation.getCar().getId();
        var receptionId = reservation.getReceptionVenue().getId();
        departmentService.removeCarFromDepartment(carId, receptionId);
        carService.setCarStatus(carId, CarStatus.HIRED);
        rentalRepository.save(rental);
    }

    public List<RentalModel> getRentalList() {
        return rentalRepository.findAll();
    }

    public List<RentalModel> getRentalListOfNotReturnedCarsAllDepartments(){
        List<Long> returnedCarsReservationsIds = returnService.getReturnModelList()
                .stream()
                .map(returnModel -> returnModel.getReservation().getId())
                .toList();
        return getRentalList()
                .stream()
                .filter(rentalModel -> rentalModel.getReservation().getReservationStatus().equals(ReservationStatus.RENTED))
                .filter(rentalModel -> !returnedCarsReservationsIds.contains(rentalModel.getId()))
                .toList();
    }

    public List<RentalModel> getRentalListByCustomerLastName(String lastName){
        if (lastName == null || lastName.isEmpty()){
            return getRentalList();
        }
        return rentalRepository.search(lastName);
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

    public RentalModel findByReservation(ReservationModel reservationModel){
        var rental = rentalRepository.findByReservation(reservationModel);
        if (rental.isPresent()){
            return rental.get();
        } else {
            return null;
        }
    }

    public List<RentalModel> getRentalListWithFilters(CustomerModel customer, LocalDate date, DepartmentModel receptionVenue, ReservationStatus reservationStatus) {
        return rentalRepository.findWithFilters(customer, date, receptionVenue, reservationStatus);
    }

    public List<RentalModel> getRentalListOfNotReturnedCarsByReturnDepartment(DepartmentModel departmentModel) {
        if (departmentModel == null){
            return getRentalListOfNotReturnedCarsAllDepartments();
        } else {
            return getRentalList()
                    .stream()
                    .filter(r -> r.getReservation().getReservationStatus().equals(ReservationStatus.RENTED))
                    .filter(r -> r.getReservation().getReturnVenue().getCity().equals(departmentModel.getCity()))
                    .toList();
        }
    }
}
