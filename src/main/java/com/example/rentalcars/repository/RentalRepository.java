package com.example.rentalcars.repository;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {
    Optional<RentalModel> findByReservation(ReservationModel reservationModel);

    @Query("select r from RentalModel r " +
            "where lower(r.reservation.customer.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<RentalModel> search(@Param("searchTerm") String searchTerm);
}
