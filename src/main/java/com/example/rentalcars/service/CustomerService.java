package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.CustomerAdditionException;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.repository.CustomerRepository;
import com.vaadin.flow.component.notification.Notification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerModel> getCustomerList() {
        return customerRepository.findAll();
    }

    public List<CustomerModel> findWithFilter(String filterText) {
            return customerRepository.search(filterText);
    }

    public CustomerModel findById(Long id) {
        var customerModel = customerRepository.findById(id);
        return customerModel.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono klienta o id = " + id));
    }

    public CustomerModel saveCustomer(CustomerModel customerModel) {
        try {
            CustomerModel savedCustomer = customerRepository.save(customerModel);
            System.out.println("Klient został dodany pomyślnie");
            return savedCustomer;
        } catch (Exception e) {
            throw new CustomerAdditionException("Błąd podczas dodawania klienta", e);
        }
    }

    public CustomerModel getCustomerByUserName(String userName) {
        var customerModel = getCustomerList().stream().filter(c -> c.getUser().getName().equals(userName)).findFirst();
        return customerModel.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono klienta"));
    }
}
