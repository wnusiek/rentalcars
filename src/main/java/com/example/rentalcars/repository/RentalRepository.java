package com.example.rentalcars.repository;

import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {
    Optional<RentalModel> findByReservation(ReservationModel reservationModel);

    @Query("select r from RentalModel r " +
            "where (:searchTerm is null or r.reservation.customer = :searchTerm)" +
            "and (:searchDate is null or r.reservation.dateFrom = :searchDate)" +
            "and (:searchDepartment is null or r.reservation.receptionVenue = :searchDepartment)" +
            "and (:searchReservationStatus is null or r.reservation.reservationStatus = :searchReservationStatus)")
    List<RentalModel> findWithFilters(
            @Param("searchTerm") CustomerModel customer,
            @Param("searchDate") LocalDate date,
            @Param("searchDepartment") DepartmentModel receptionVenue,
            @Param("searchReservationStatus") ReservationStatus reservationStatus);
}
