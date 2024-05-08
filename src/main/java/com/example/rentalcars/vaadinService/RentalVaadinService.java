package com.example.rentalcars.vaadinService;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.MenagementServices.CarsByDateService;
import com.example.rentalcars.MenagementServices.CarsBySpecificationService;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalVaadinService {
    private final CarsBySpecificationService carsBySpecificationService;
    private final CarService carService;
    private final DepartmentService departmentService;
    private final ReservationService reservationService;

    public List<CarDto> findCarsByMark(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return carService.getCarList();
        } else {
            return carsBySpecificationService.getCarsByMark(filterText);

        }
    }

    public List<CarModel> findAvailableCarsByDates (DepartmentModel departmentModel, LocalDate startDate, LocalDate endDate){
        try {
            return reservationService.getAvailableCarsByDateRange(findCarsByDepartment(departmentModel),startDate, endDate);
        } catch (NullPointerException n) {
            return findCarsByDepartment(departmentModel);
        }
    }

    public List<CarModel> findCarsByDepartment (DepartmentModel departmentModel){
        try {
            return carService.getAvailableCars(departmentService.getAllCarsByDepartment(departmentModel.getId()));
        } catch (NullPointerException n) {
            return carService.getAvailableCars(carService.getCarList1());
        }
    }

}
