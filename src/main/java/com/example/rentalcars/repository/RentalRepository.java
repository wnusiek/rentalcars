package com.example.rentalcars.repository;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {
    Optional<RentalModel> findByReservation(ReservationModel reservationModel);
}
