package com.example.rentalcars.ManagmentControllers;


import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarManagementController {

    private final CarRepository carRepository;

    private final DepartmentRepository departmentRepository;

    @PostMapping("/addCarToDepartment/{carId}/{departmentId}")
    public void addCarToDepartment(@PathVariable Long carId,@PathVariable Long departmentId){

        var department = departmentRepository.findById(departmentId);
        var car = carRepository.findById(carId);

        if(car.isPresent() && department.isPresent()){
            var c = car.get();
            var d = department.get();
            d.getCars().add(c);
            departmentRepository.save(d);
        }

    }

    @PostMapping("/setMileageByCarId/{carId}")
    public void setMileageByCarId(@PathVariable Long carId, Integer mileage){
        var car = carRepository.findById(carId);
        if (car.isPresent()){
            var c = car.get();
            c.setMileage(mileage);
            carRepository.save(c);
        }
    }

}
