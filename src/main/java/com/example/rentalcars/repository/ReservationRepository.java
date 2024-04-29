package com.example.rentalcars.repository;

import com.example.rentalcars.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long > {
    @Query("select r from ReservationModel r where lower(r.customer.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<ReservationModel> search(@Param("searchTerm") String searchTerm);

    @Query("select r from ReservationModel r where lower(r.customer.lastName) like lower(concat('%', :searchTerm, '%')) and r.dateFrom = :searchDate")
    List<ReservationModel> findByReservationDateFrom(@Param("searchTerm") String searchTerm, @Param("searchDate") LocalDate date);
}
