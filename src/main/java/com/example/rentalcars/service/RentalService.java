package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.RentalAdditionException;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ReturnService returnService;

    public RentalModel addRental(RentalModel rental) {
        try {
            RentalModel savedRental = rentalRepository.save(rental);
            System.out.println("Wypożyczenie zostało dodane pomyślnie");
            return savedRental;
        } catch (Exception e) {
            throw new RentalAdditionException("Błąd podczas dodawania wypożyczenia.", e);
        }
    }

    public List<RentalModel> getRentalList() {
        return rentalRepository.findAll();
    }

    public RentalModel findById(Long id) {
        var rental = rentalRepository.findById(id);
        return rental.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono wypożyczenia" + id));
    }

    public void updateRental(RentalModel rental) {
        rentalRepository.save(rental);
    }

    public void removeRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public List<RentalModel> getRentalListOfNotReturnedCarsAllDepartments() {
        return getRentalList()
                .stream()
                .filter(rentalModel -> rentalModel.getReservation().getReservationStatus().equals(ReservationStatus.RENTED))
                .filter(rentalModel -> !getCarsReservationsIds().contains(rentalModel.getId()))
                .toList();
    }

    private List<Long> getCarsReservationsIds() {
        List<Long> returnedCarsReservationsIds = returnService.getReturnModelList()
                .stream()
                .map(returnModel -> returnModel.getReservation().getId())
                .toList();
        return returnedCarsReservationsIds;
    }

    public RentalModel findByReservation(ReservationModel reservationModel) {
        var rental = rentalRepository.findByReservation(reservationModel);
        return rental.orElse(null);
    }

    public List<RentalModel> getRentalListWithFilters(CustomerModel customer, LocalDate date, DepartmentModel receptionVenue, ReservationStatus reservationStatus) {
        return rentalRepository.findWithFilters(customer, date, receptionVenue, reservationStatus);
    }

    public List<RentalModel> getRentalListOfNotReturnedCarsByReturnDepartment(DepartmentModel departmentModel) {
        if (departmentModel == null) {
            return getRentalListOfNotReturnedCarsAllDepartments();
        } else {
            return getRentalList()
                    .stream()
                    .filter(r -> r.getReservation().getReservationStatus().equals(ReservationStatus.RENTED))
                    .filter(r -> r.getReservation().getReturnVenue().getCity().equals(departmentModel.getCity()))
                    .toList();
        }
    }

    List<Long> getRentedCarsReservationsIds() {
        return getRentalList()
                .stream()
                .map(rentalModel -> rentalModel.getReservation().getId())
                .toList();
    }
}
