package com.example.rentalcars.repository;

import com.example.rentalcars.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long > {
    @Query("select r from ReservationModel r " +
            "where lower(r.customer.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<ReservationModel> search(@Param("searchTerm") String searchTerm);
}
