package com.example.rentalcars.repository;

import com.example.rentalcars.model.SettlementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<SettlementModel, Long> {
}
