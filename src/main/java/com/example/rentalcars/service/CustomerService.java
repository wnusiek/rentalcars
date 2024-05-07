package com.example.rentalcars.service;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.repository.CustomerRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void postAddCustomer(CustomerModel customer) {
        customerRepository.save(customer);
    }

    public List<CustomerModel> getCustomerList() {
        return customerRepository.findAll();
    }

    public List<CustomerModel> findWithFilter(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return getCustomerList();
        } else {
            return customerRepository.search(filterText);
        }
    }

    public CustomerModel findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void saveCustomer(CustomerModel customerModel) {
        if (customerModel == null) {
            System.err.println("Employee is null.");
        } else {
            customerRepository.save(customerModel);
        }
    }

    public CustomerModel getCustomerByUserName(String userName) {
        var customer = getCustomerList().stream().filter(customerModel -> customerModel.getUser().getName().equals(userName)).findFirst();
        if (customer.isPresent())
            return customer.get();
        else {
            System.err.println("Nie ma takiego klienta");
            Notification.show("Nie ma takiego klienta").setPosition(Notification.Position.MIDDLE);
            return null;
        }
    }


}
