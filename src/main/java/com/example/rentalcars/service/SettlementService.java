package com.example.rentalcars.service;

import com.example.rentalcars.model.SettlementModel;
import com.example.rentalcars.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;
    public List<SettlementModel> getSettlementByCustomer() {
        return settlementRepository.findAll();
    }
}
