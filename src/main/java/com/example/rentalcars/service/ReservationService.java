package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.ReservationAdditionException;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.ReservationRepository;
import com.vaadin.flow.component.notification.Notification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final RentalService rentalService;

    public ReservationModel addReservation(ReservationModel reservation) {
        try {
            return reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new ReservationAdditionException("Błąd podczas dodawania rezerwacji", e);
        }
    }

    public List<ReservationModel> getReservationList() {
        return reservationRepository.findAll();
    }


    public List<ReservationModel> getReservationListOfNotRentedCarsByReceptionDepartment(DepartmentModel departmentModel) {
        if (departmentModel == null) {
            return getReservationListOfNotRentedCarsAllReceptionDepartments();
        } else {
            return getReservationList()
                    .stream()
                    .filter(r -> r.getReservationStatus().equals(ReservationStatus.RESERVED))
                    .filter(r -> r.getReceptionVenue().getCity().equals(departmentModel.getCity()))
                    .toList();
        }
    }

    public List<ReservationModel> getReservationListOfNotRentedCarsAllReceptionDepartments() {
        return getReservationList()
                .stream()
                .filter(reservationModel -> reservationModel.getReservationStatus().equals(ReservationStatus.RESERVED))
                .filter(reservationModel -> !rentalService.getRentedCarsReservationsIds().contains(reservationModel.getId()))
                .toList();
    }

    public void editReservation(ReservationModel editReservation) {
        reservationRepository.save(editReservation);
    }

    public void removeReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Optional<ReservationModel> findById(Long id) {
        return reservationRepository.findById(id);
    }

    List<ReservationModel> getReservationListByCarId(Long carId) {
        return getReservationList().stream()
                .filter(r -> r.getCar().getId().equals(carId))
                .collect(Collectors.toList());
    }

    public Boolean isCarAvailableInGivenDateRange(Long carId, LocalDate dateFrom, LocalDate dateTo) {
        var reservationList = getReservationListByCarId(carId);
        for (ReservationModel r : reservationList) {
            if (dateFrom.equals(r.getDateFrom()) || dateFrom.equals(r.getDateTo()) || dateTo.equals(r.getDateFrom()) || dateTo.equals(r.getDateTo())) {
                return false;
            } else if ((dateFrom.isAfter(r.getDateFrom()) && dateFrom.isBefore(r.getDateTo())) || (dateTo.isBefore(r.getDateTo()) && dateTo.isAfter(r.getDateFrom()))
                    || (dateFrom.isBefore(r.getDateFrom()) && dateTo.isAfter(r.getDateTo()))) {
                return false;
            }
        }
        return true;
    }

    public List<CarModel> getAvailableCarsByDateRange(List<CarModel> cars, LocalDate dateFrom, LocalDate dateTo) {
        List<CarModel> availableInDateRangeCarList = new ArrayList<>();

        for (CarModel c : cars) {
            if (isCarAvailableInGivenDateRange(c.getId(), dateFrom, dateTo)) {
                availableInDateRangeCarList.add(c);
            }
        }
        return availableInDateRangeCarList;
    }

    public BigDecimal calculateRentalCost(ReservationModel reservation) {
        BigDecimal dailyRentalPrice = reservation.getCar().getPrice();
        LocalDate startDate = reservation.getDateFrom();
        LocalDate endDate = reservation.getDateTo();

        // Obliczamy różnicę między datami i zaokrąglamy w górę do pełnych dni
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // Obliczamy koszt rezerwacji (cena za dzień * liczba dni)
        BigDecimal totalCost = dailyRentalPrice.multiply(BigDecimal.valueOf(numberOfDays));

        if (reservation.getReceptionVenue().equals(reservation.getReturnVenue())) {
            return totalCost;
        } else {
            return totalCost.add(BigDecimal.valueOf(100l));
        }
    }

    public List<ReservationModel> getReservationListLoggedUser() {
        String loggedUserName = userService.getNameOfLoggedUser();
        return getReservationList().stream().filter(reservation -> reservation.getCustomer().getUser().getName().equals(loggedUserName)).toList();
    }

    public void cancelReservation(ReservationModel reservationModel) {
        LocalDate reservationStartDate = reservationModel.getDateFrom();
        LocalDate today = LocalDate.now();
        Long difference = ChronoUnit.DAYS.between(today, reservationStartDate);

        if (today.isBefore(reservationStartDate) && difference >= 2) {
            if (reservationModel.getReservationStatus().equals(ReservationStatus.RESERVED)) {
                Notification.show("Rezerwacja anulowana bez opłat").setPosition(Notification.Position.MIDDLE);
                reservationModel.setReservationStatus(ReservationStatus.CANCELLED);
                reservationModel.setPrice(BigDecimal.valueOf(0));
                editReservation(reservationModel);
            }
        } else if (difference < 2 && difference >= 0) {
            if (reservationModel.getReservationStatus().equals(ReservationStatus.RESERVED)) {
                Notification.show("Zostanie pobrana opłata 20% ceny rezerwacji").setPosition(Notification.Position.MIDDLE);
                reservationModel.setReservationStatus(ReservationStatus.CANCELLED);
                BigDecimal handlingFee = reservationModel.getPrice().multiply(BigDecimal.valueOf(0.2));
                reservationModel.setPrice(handlingFee);
                editReservation(reservationModel);
            } else {
                Notification.show("Ta rezerwacja została już anulowana").setPosition(Notification.Position.MIDDLE);
            }
        } else {
            Notification.show("Nie można anulować tej rezerwacji").setPosition(Notification.Position.MIDDLE);
        }
    }

    public void setReservationStatus(Long reservationId, ReservationStatus reservationStatus) {
        try {
            var reservation = findById(reservationId);
            if (reservation.isPresent()) {
                var r = reservation.get();
                r.setReservationStatus(reservationStatus);
                editReservation(r);
            }
        } catch (Exception e) {
            throw new EntityNotFoundException("Wystąpił błąd. Status rezerwacji nie został zmieniony");
        }
    }

    public void cancelOutdatedReservationOfNotRentedCar() {
        List<ReservationModel> outdatedReservations = reservationRepository.findAll().stream()
                .filter(r -> r.getReservationStatus().equals(ReservationStatus.RESERVED))
                .filter(r -> r.getDateFrom().isBefore(LocalDate.now())).collect(Collectors.toList());
        outdatedReservations.forEach(r -> r.setReservationStatus(ReservationStatus.CANCELLED));
        outdatedReservations.forEach(r -> editReservation(r));
    }

    public List<ReservationModel> getReservationListWithFilters(CustomerModel customer, LocalDate date, DepartmentModel receptionVenue, ReservationStatus reservationStatus) {
        return reservationRepository.findWithFilters(customer, date, receptionVenue, reservationStatus);
    }
}
