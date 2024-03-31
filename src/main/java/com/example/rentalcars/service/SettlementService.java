package com.example.rentalcars.service;

import com.example.rentalcars.model.SettlementModel;
import com.example.rentalcars.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;
    private final UserService userService;
    private final CustomerService customerService;

    public boolean addSettlementModel(SettlementModel settlementModel){
        if (settlementModel == null){
            return false;
        } else {
            settlementRepository.save(settlementModel);
            return true;
        }
    }
    public List<SettlementModel> getSettlementByReservationCustomer(){
        Optional<List<SettlementModel>> customerSettlement = settlementRepository.findByReservationCustomer(customerService.getCustomerByUserName(userService.getNameOfLoggedUser()));

        if (customerSettlement.isPresent()){
            return customerSettlement.get();
        } else
            return Collections.emptyList();
    }

}
