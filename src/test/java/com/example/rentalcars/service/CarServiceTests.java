package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.CarAdditionException;
import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {
    private CarService carService;
    private CarRepository carRepository;
    private CarModel carModel1;
    private CarModel carModel2;
    private CarModel carModel3;
    private Long carId1;
    @BeforeEach
    public void setUp(){
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
        carId1 = 1L;
        carModel1 = new CarModel(carId1, "Opel", "Corsa", BigDecimal.valueOf(100), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004, "");
        carModel2 = new CarModel(2L, "Opel", "Astra", BigDecimal.valueOf(200), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019, "");
        carModel3 = new CarModel();
        carModel3.setAvailability(CarStatus.UNAVAILABLE);
    }

    @Test
    public void givenCarModel_whenSaveCar_thenReturnCarModel(){
        given(carRepository.save(carModel1)).willReturn(carModel1);
        var savedCar = carService.saveCar(carModel1);
        assertThat(savedCar).isNotNull();
    }

    @Test
    public void givenCarModel_whenSaveCar_thenExceptionThrown(){
        given(carRepository.save(carModel1)).willThrow(new RuntimeException());
        assertThrows(CarAdditionException.class, () -> carService.saveCar(carModel1));
    }

    @Test
    public void testPostAddCar() {
        given(carRepository.save(carModel1)).willReturn(carModel1);
        carService.postAddCar(carModel1);
        verify(carRepository, times(1)).save(carModel1);
    }

    @Test
    public void givenCarList_whenGetCarList_thenReturnCarList(){
        given(carRepository.findAll()).willReturn(List.of(carModel1, carModel2));
        var carModelList = carService.getCarList1();
        assertThat(carModelList).containsExactlyInAnyOrder(carModel1, carModel2);
    }

    @Test
    public void givenEmptyCarList_whenGetCarList_thenReturnEmptyList(){
        given(carRepository.findAll()).willReturn(Collections.emptyList());
        var carModelList = carService.getCarList1();
        assertThat(carModelList).isEmpty();
    }

    @Test
    public void testDeleteCar_Successfully(){
        carService.deleteCar(carModel1);
        verify(carRepository, times(1)).delete(carModel1);
    }

    @Test
    public void testDeleteCar_VerifyDeleted(){
        willDoNothing().given(carRepository).delete(carModel1);
        carService.deleteCar(carModel1);
        verify(carRepository, times(1)).delete(carModel1);
    }

    @Test
    public void testFindCarById_CarFound(){
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        var car = carService.findById(carId1);
        assertThat(car).isEqualTo(carModel1);
    }

    @Test
    public void testFindCarById_ExceptionThrown(){
        given(carRepository.findById(carId1)).willThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> carService.findById(carId1));
    }

    @Test
    public void testGetAvailableCars_SomeAvailableCars(){
        var availableCars = carService.getAvailableCars(List.of(carModel1, carModel2, carModel3));
        assertThat(availableCars).containsExactlyInAnyOrder(carModel1, carModel2);
    }

    @Test
    public void testGetAvailableCars_NoAvailableCars(){
        var availableCars = carService.getAvailableCars(List.of(carModel3));
        assertThat(availableCars).isEmpty();
    }

    @Test
    public void testGetAvailableCars_NoCars(){
        var availableCars = carService.getAvailableCars(List.of());
        assertThat(availableCars).isEmpty();
    }

    @Test
    public void testSetCarStatus_CarExists(){
        var newStatus = CarStatus.UNAVAILABLE;
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        carService.setCarStatus(carId1, newStatus);
        assertThat(carModel1.getAvailability()).isEqualTo(newStatus);
        verify(carRepository, times(1)).save(carModel1);
    }

    @Test
    public void testSetCarStatus_CarDoesNotExist(){
        var newStatus = CarStatus.HIRED;
        given(carRepository.findById(carId1)).willReturn(Optional.empty());
        assertDoesNotThrow(() -> carService.setCarStatus(carId1, newStatus));
        verify(carRepository, never()).save(any(CarModel.class));
    }

    @Test
    public void testSetMileage_CarExists(){
        var newMileage = 1;
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        carService.setMileageByCarId(carId1, newMileage);
        assertThat(carModel1.getMileage()).isEqualTo(newMileage);
        verify(carRepository, times(1)).save(carModel1);
    }

    @Test
    public void testSetMileage_CarDoesNotExist(){
        var newMileage = 1;
        given(carRepository.findById(carId1)).willReturn(Optional.empty());
        assertDoesNotThrow(() -> carService.setMileageByCarId(carId1, newMileage));
        verify(carRepository, never()).save(any(CarModel.class));
    }

    @Test
    public void testFindWithFilter_NoCriteria_NoEmptyResult(){
        given(carRepository.search(null, null, null, null)).willReturn(List.of(carModel1, carModel2, carModel3));
        var carModelList = carService.findWithFilter(null, null, null, null);
        assertFalse(carModelList.isEmpty());
    }

    @Test
    public void testUpdateCar_Success() {
        given(carRepository.save(carModel1)).willReturn(carModel1);
        carModel1.setMileage(1000);
        carService.updateCar(carModel1);
        verify(carRepository, times(1)).save(carModel1);
        assertThat(carModel1.getMileage()).isEqualTo(1000);
    }

    @Test
    public void testRemoveCar_Success() {
        willDoNothing().given(carRepository).deleteById(carId1);
        carService.removeCar(carId1);
        verify(carRepository, times(1)).deleteById(carId1);
    }
}
