package com.example.rentalcars.service;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrmServiceTests {
    @InjectMocks
    private CrmService crmService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private ReturnRepository returnRepository;
    private Long expectedCount;
    @BeforeEach
    public void setup() {
        expectedCount = 10L;
    }

    @Test
    public void testCountCars(){
        when(carRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countCars();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(carRepository).count();
    }

    @Test
    public void testCountEmployees() {
        when(employeeRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countEmployees();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(employeeRepository).count();
    }

    @Test
    public void testCountCustomers() {
        when(customerRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countCustomers();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(customerRepository).count();
    }

    @Test
    public void testCountReservations() {
        when(reservationRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countReservations();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(reservationRepository).count();
    }

    @Test
    public void testCountRentals() {
        when(rentalRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countRentals();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(rentalRepository).count();
    }

    @Test
    public void testCountReturns() {
        when(returnRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countReturns();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(returnRepository).count();
    }

    @Test
    public void testCountDepartments() {
        when(departmentRepository.count()).thenReturn(expectedCount);
        Long actualCount = crmService.countDepartments();
        assertThat(expectedCount).isEqualTo(actualCount);
        verify(departmentRepository).count();
    }
}
