package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.CustomerAdditionException;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private CustomerModel customerModel;
    private UserModel userModel;
    private RoleModel roleModel;

    @BeforeEach
    public void setup(){
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
        roleModel = new RoleModel(1L, "USER");
        userModel = new UserModel(1L, "bolek","bolek1111","bolek@gmail.com", true, roleModel);
        customerModel = new CustomerModel(1L, "Bolesław", "Śmiały", "111111111", "qwerty1234", "33333333333", "Kraków", "11-111", userModel);
    }

    @Test
    public void testGetCustomerList_ReturnCustomerList() {
        CustomerModel customerModel1 = new CustomerModel();
        when(customerRepository.findAll()).thenReturn(List.of(customerModel, customerModel1));

        List<CustomerModel> savedCustomerList = customerService.getCustomerList();

        assertThat(savedCustomerList.size()).isEqualTo(2);
    }

    @Test
    public void testGetCustomerList_ReturnEmptyList() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerModel> savedCustomerList = customerService.getCustomerList();

        assertThat(savedCustomerList).isEmpty();
    }

    @Test
    public void testFindWithFilter_NoCriteria_NoEmptyResult() {
        when(customerRepository.search(null)).thenReturn(List.of(new CustomerModel(), new CustomerModel()));
        List<CustomerModel> savedCustomerList = customerService.findWithFilter(null);
        assertFalse(savedCustomerList.isEmpty());
        assertThat(savedCustomerList.size()).isEqualTo(2);
    }

    @Test
    public void testFindById_CustomerFound() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerModel));
        CustomerModel savedCustomer = customerService.findById(id);
        assertThat(savedCustomer).isNotNull().isEqualTo(customerModel);
    }

    @Test
    public void testFindById_ExceptionThrown() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.findById(id));
    }

    @Test
    public void testSaveCustomer_CustomerSaved(){
        when(customerRepository.save(customerModel)).thenReturn(customerModel);
        CustomerModel savedCustomer = customerService.saveCustomer(customerModel);
        assertThat(savedCustomer).isNotNull().isEqualTo(customerModel);
    }

    @Test
    public void testSaveCustomer_ExceptionThrown(){
        when(customerRepository.save(customerModel)).thenThrow(new RuntimeException());
        assertThrows(CustomerAdditionException.class, () -> customerService.saveCustomer(customerModel));
    }

    @Test
    public void testGetCustomerByUserName_CustomerFound() {
        CustomerModel customerModel1 = new CustomerModel(2L, "", "", "", "", "", "", "",
                new UserModel(2L, "Kolec", "", "", true, new RoleModel(2L, "ADMIN")));
        CustomerModel customerModel2 = new CustomerModel(3L, "", "", "", "", "", "", "",
                new UserModel(3L, "Stolec", "", "", true, new RoleModel(2L, "ADMIN")));
        String userName = "Stolec";
        when(customerService.getCustomerList()).thenReturn(List.of(customerModel, customerModel1, customerModel2));
        CustomerModel customer = customerService.getCustomerByUserName(userName);
        assertThat(customer).isNotNull();
        assertThat(customer.getUser().getName()).isEqualTo(userName);
    }

    @Test
    public void testGetCustomerByUserName_ExceptionThrown() {
        when(customerService.getCustomerList()).thenThrow(new EntityNotFoundException());
        String userName = "Bolec";
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerByUserName(userName));
    }
}
