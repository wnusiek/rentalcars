package com.example.rentalcars.service;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private UserService userService;

    public void postAddCustomer(CustomerModel customer) {
        customerRepository.save(customer);
    }

    public List<CustomerModel> getCustomerList() {
        return customerRepository.findAll();
    }

    public CustomerModel findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void updateCustomer(CustomerModel customer) {
        customerRepository.save(customer);
    }

    public void removeCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    public void saveCustomer(CustomerModel customerModel){
        if (customerModel == null ){
            System.err.println("Employee is null.");
            return;
        }else {
            customerRepository.save(customerModel);
        }
    }

    public void deleteCustomer(CustomerModel customerModel){
        customerRepository.delete(customerModel);
    }

    public CustomerModel getCustomerByUserName(String userName){
        var customer = getCustomerList().stream().filter(customerModel -> customerModel.getUser().getName().equals(userName)).findFirst();
        if (customer.isPresent())
            return customer.get();
        else {
            System.err.println("Nie ma takiego klienta");
            return null;
        }

    }

    public boolean checkIfCustomerExist(){
        if(getCustomerByUserName(userService.getNameOfLoggedUser()).equals(null)){
            return false;
        }
        return true;
    }


}
