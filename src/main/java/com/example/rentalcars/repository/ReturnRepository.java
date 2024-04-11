package com.example.rentalcars.repository;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnRepository extends JpaRepository<ReturnModel, Long> {
    Optional<ReturnModel> findByReservation(ReservationModel reservationModel);
}
