package com.example.rentalcars.repository;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.SettlementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<SettlementModel, Long> {
    Optional<List<SettlementModel>> findByReservationCustomer(CustomerModel customerModel);
}
