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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {
    private CarService carService;
    private CarRepository carRepository;
    private CarModel carModel;
    @BeforeEach
    public void setUp(){
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
        carModel = new CarModel(1L, "Opel", "Corsa", BigDecimal.valueOf(100), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004);
    }

    @Test
    public void givenCarModel_whenSaveCar_thenReturnCarModel(){
        when(carRepository.save(carModel)).thenReturn(carModel);
        CarModel savedCar = carService.saveCar(carModel);
        assertThat(savedCar).isNotNull();
    }

    @Test
    public void givenCarModel_whenSaveCar_thenExceptionThrown(){
        when(carRepository.save(carModel)).thenThrow(new RuntimeException());
        assertThrows(CarAdditionException.class, () -> carService.saveCar(carModel));
    }

    @Test
    public void testPostAddCar() {
        when(carRepository.save(carModel)).thenReturn(carModel);
        carService.postAddCar(carModel);
        verify(carRepository, times(1)).save(carModel);
    }

    @Test
    public void givenCarList_whenGetCarList_thenReturnCarList(){
        CarModel carModel1 = new CarModel(2L, "Opel", "Astra", BigDecimal.valueOf(200), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019);
        given(carRepository.findAll()).willReturn(List.of(carModel, carModel1));

        List<CarModel> carModelList = carService.getCarList1();

        assertThat(carModelList).isNotNull();
        assertThat(carModelList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyCarList_whenGetCarList_thenReturnEmptyList(){
        given(carRepository.findAll()).willReturn(Collections.emptyList());

        List<CarModel> carModelList = carService.getCarList1();

        assertThat(carModelList).isEmpty();
    }

    @Test
    public void testDeleteCar_Successfully(){
        carService.deleteCar(carModel);
        verify(carRepository, times(1)).delete(carModel);
    }

    @Test
    public void testDeleteCar_VerifyDeleted(){
        carService.deleteCar(carModel);

        Optional<CarModel> deletedCar = carRepository.findById(carModel.getId());

        assertTrue(deletedCar.isEmpty());
    }

    @Test
    public void testFindCarById_CarFound(){
        given(carRepository.findById(carModel.getId())).willReturn(Optional.of(carModel));

        CarModel car = carService.findById(carModel.getId());

        assertThat(car).isNotNull();
        assertThat(car).isEqualTo(carModel);
    }

    @Test
    public void testFindCarById_ExceptionThrown(){
        Long id = 1L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> carService.findById(id));
    }

    @Test
    public void testGetAvailableCars_SomeAvailableCars(){
        List<CarModel> cars = new ArrayList<>();
        cars.add(new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(100), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004));
        cars.add(new CarModel(2l, "Opel", "Astra", BigDecimal.valueOf(200), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        cars.add(new CarModel(3l, "BMW", "E3śmieć", BigDecimal.valueOf(400), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.HIRED, "Black", 45000, 2019));
        cars.add(new CarModel(4l, "AUDI", "80", BigDecimal.valueOf(300), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.UNAVAILABLE, "Black", 45000, 2019));

        List<CarModel> availableCars = carService.getAvailableCars(cars);

        assertThat(availableCars.size()).isEqualTo(2);
    }

    @Test
    public void testGetAvailableCars_NoAvailableCars(){
        List<CarModel> cars = new ArrayList<>();
        cars.add(new CarModel(3l, "BMW", "E3śmieć", BigDecimal.valueOf(400), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.HIRED, "Black", 45000, 2019));
        cars.add(new CarModel(4l, "AUDI", "80", BigDecimal.valueOf(300), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.UNAVAILABLE, "Black", 45000, 2019));

        List<CarModel> availableCars = carService.getAvailableCars(cars);

        assertThat(availableCars).isEmpty();
    }

    @Test
    public void testGetAvailableCars_NoCars(){
        List<CarModel> cars = new ArrayList<>();

        List<CarModel> availableCars = carService.getAvailableCars(cars);

        assertThat(availableCars).isEmpty();
    }

    @Test
    public void testSetCarStatus_CarExists(){
        Long id = carModel.getId();
        CarStatus newStatus = CarStatus.UNAVAILABLE;
        when(carRepository.findById(id)).thenReturn(Optional.of(carModel));

        carService.setCarStatus(id, newStatus);

        assertThat(carModel.getAvailability()).isEqualTo(newStatus);
        verify(carRepository, times(1)).save(carModel);
    }

    @Test
    public void testSetCarStatus_CarDoesNotExist(){
        Long id = 1L;
        CarStatus newStatus = CarStatus.HIRED;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> carService.setCarStatus(id, newStatus));

        verify(carRepository, never()).save(any());
    }

    @Test
    public void testSetMileage_CarExists(){
        Long id = carModel.getId();
        Integer newMileage = 1;
        when(carRepository.findById(id)).thenReturn(Optional.of(carModel));

        carService.setMileageByCarId(id, newMileage);

        assertThat(carModel.getMileage()).isEqualTo(newMileage);
        verify(carRepository, times(1)).save(carModel);
    }

    @Test
    public void testSetMileage_CarDoesNotExist(){
        Long id = 1L;
        Integer newMileage = 1;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> carService.setMileageByCarId(id, newMileage));

        verify(carRepository, never()).save(any());
    }

    @Test
    public void testFindWithFilter_NoCriteria_NoEmptyResult(){
        when(carRepository.search(null, null, null, null)).thenReturn(Arrays.asList(new CarModel(), new CarModel()));

        List<CarModel> carModelList = carService.findWithFilter(null, null, null, null);

        assertFalse(carModelList.isEmpty());
    }

    @Test
    public void testUpdateCar_Success() {
        when(carRepository.save(carModel)).thenReturn(carModel);
        carModel.setMileage(1000);
        carService.updateCar(carModel);
        verify(carRepository, times(1)).save(carModel);
        assertThat(carModel.getMileage()).isEqualTo(1000);
    }

    @Test
    public void testRemoveCar_Success() {
        Long carId = 1L;
        willDoNothing().given(carRepository).deleteById(carId);
        carService.removeCar(carId);
        verify(carRepository, times(1)).deleteById(carId);
    }
}
