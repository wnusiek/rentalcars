package com.example.rentalcars.service;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;

    public void addReservation(ReservationModel reservation) {
        reservationRepository.save(reservation);
    }

    public List<ReservationModel> getReservationList() {
        return reservationRepository.findAll();
    }

    public void editReservation(ReservationModel editReservation) {
        reservationRepository.save(editReservation);
    }

    public void removeReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationModel findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    private List<ReservationModel> getReservationListByCarId(Long carId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getCarModel().getId().equals(carId))
                .collect(Collectors.toList());
    }

    public Boolean getCarAvailabilityByDateRange(Long carId, LocalDate dateFrom, LocalDate dateTo) {
        var reservationList = getReservationListByCarId(carId);
        for (ReservationModel r : reservationList) {
            if (dateFrom.isAfter(r.getDateTo()) || dateTo.isBefore(r.getDateFrom())) {
                return true;
            }
        }
        return false;
    }

    public List<CarModel> getAvailableCarsByDateRange(LocalDate dateFrom, LocalDate dateTo) {
        List<CarModel> availableCarList = new ArrayList<>();
        List<CarModel> cars = carRepository.findAll();

        for (CarModel c : cars) {
            if (getCarAvailabilityByDateRange(c.getId(), dateFrom, dateTo)) {
                availableCarList.add(c);
            }
        }
        return availableCarList;
    }

}
