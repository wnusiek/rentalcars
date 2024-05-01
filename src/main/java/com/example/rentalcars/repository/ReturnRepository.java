package com.example.rentalcars.repository;

import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReturnRepository extends JpaRepository<ReturnModel, Long> {
    Optional<ReturnModel> findByReservation(ReservationModel reservationModel);

    @Query("select r from ReturnModel r " +
            "where lower(r.reservation.customer.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<ReturnModel> search(@Param("searchTerm") String searchTerm);

    @Query("select r from ReturnModel r " +
            "where (:searchTerm is null or r.reservation.customer = :searchTerm)" +
            "and (:searchDate is null or r.reservation.dateFrom = :searchDate)" +
            "and (:searchDepartment is null or r.reservation.receptionVenue = :searchDepartment)" +
            "and (:searchReservationStatus is null or r.reservation.reservationStatus = :searchReservationStatus)")
    List<ReturnModel> findWithFilters(
            @Param("searchTerm") CustomerModel customer,
            @Param("searchDate") LocalDate date,
            @Param("searchDepartment") DepartmentModel receptionVenue,
            @Param("searchReservationStatus") ReservationStatus reservationStatus);
}
