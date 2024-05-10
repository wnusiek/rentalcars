package com.example.rentalcars.service;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<CarModel> getCarList1() {
        return carRepository.findAll();
    }

    public void saveCar(CarModel carModel) {
        if (carModel == null) {
            System.err.println("Car is null.");
            Notification.show("Null. Samochód nie został dodany").setPosition(Notification.Position.MIDDLE);
        }
        carRepository.save(carModel);
        Notification.show("Samochód został dodany pomyślnie").setPosition(Notification.Position.MIDDLE);
    }

    public void deleteCar(CarModel carModel) {
        carRepository.delete(carModel);
    }

    public void postAddCar(CarModel car) {
        carRepository.save(car);
    }

    public List<CarDto> getCarList() {
        return carRepository.findAll().stream().map(i -> new CarDto(i.getId(), i.getMark(), i.getModel(), i.getAvailability(), i.getMileage(), i.getProductionDate(), i.getPrice(), i.getBody(), i.getColor(), i.getFuelType(), i.getGearbox())).toList();
    }

    public CarModel findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public void updateCar(CarModel car) {
        carRepository.save(car);
    }

    public void removeCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<CarModel> getAvailableCars(List<CarModel> cars) {
        return cars.stream()
                .filter(car -> car.getAvailability() != null)
                .filter(car -> car.getAvailability().equals(CarStatus.AVAILABLE))
                .toList();
    }

    public void setCarStatus(Long id, CarStatus carStatus) {
        var car = carRepository.findById(id);
        if (car.isPresent()) {
            var c = car.get();
            c.setAvailability(carStatus);
            carRepository.save(c);
        }
    }

    public List<CarModel> findWithFilter(String filterText, BodyType bodyType, GearboxType gearboxType, FuelType fuelType) {
        return carRepository.search(filterText, bodyType, gearboxType, fuelType);
    }

    public void setMileageByCarId(Long carId, Integer mileage){
        var car = carRepository.findById(carId);
        if (car.isPresent()) {
            var c = car.get();
            c.setMileage(mileage);
            carRepository.save(c);
        }
    }

}
