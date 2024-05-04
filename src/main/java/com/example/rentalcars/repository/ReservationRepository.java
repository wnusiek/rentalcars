package com.example.rentalcars.repository;

import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long > {
    @Query("select r from ReservationModel r " +
            "where lower(r.customer.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<ReservationModel> search(@Param("searchTerm") String searchTerm);

    @Query("select r from ReservationModel r " +
            "where (:searchTerm is null or r.customer = :searchTerm)" +
            "and (:searchDate is null or r.dateFrom = :searchDate)" +
            "and (:searchDepartment is null or r.receptionVenue = :searchDepartment)" +
            "and (:searchReservationStatus is null or r.reservationStatus = :searchReservationStatus)"
    )
    List<ReservationModel> findWithFilters(
            @Param("searchTerm") CustomerModel customer,
            @Param("searchDate") LocalDate date,
            @Param("searchDepartment") DepartmentModel receptionVenue,
            @Param("searchReservationStatus") ReservationStatus reservationStatus);
}
