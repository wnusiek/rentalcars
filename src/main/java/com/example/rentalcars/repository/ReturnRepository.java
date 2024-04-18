package com.example.rentalcars.repository;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReturnRepository extends JpaRepository<ReturnModel, Long> {
    Optional<ReturnModel> findByReservation(ReservationModel reservationModel);

    @Query("select r from ReturnModel r " +
            "where lower(r.reservation.customer.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<ReturnModel> search(@Param("searchTerm") String searchTerm);
}
