package com.example.rentalcars.service;

import com.example.rentalcars.DTO.DepartmentDTO;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final CarRepository carRepository;

    public void postAddDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public List<DepartmentModel> getDepartmentList1() {
        return departmentRepository.findAll();
    }
    public List<DepartmentDTO> getDepartmentList() {
        return departmentRepository.findAll().stream().map(i -> new DepartmentDTO(i.getId(), i.getCity(), i.getCars())).toList();
    }

    public DepartmentModel findById(Long id) {
        var dep = departmentRepository.findById(id);
        var d = dep.get();
        if (dep.isPresent()){
            return d;
        } else{
       return new DepartmentModel();
        }
    }

    public void updateDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public void removeDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public void deleteDepartment(DepartmentModel departmentModel){
        departmentRepository.delete(departmentModel);
    }

    public Set<EmployeeModel> getDepartmentEmployees(Long departmentId) {
        return departmentRepository.findAll().stream()
                .filter(department -> department.getId() != null && department.getId().equals(departmentId))
                .map(DepartmentModel::getEmployees)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public List<CarModel> getAllCarsByDepartment(Long departmentId) {
        return departmentRepository.findAll().stream()
                .filter(department -> department.getId() != null && department.getId().equals(departmentId))
                .map(DepartmentModel::getCars)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    public void addCarToDepartment(Long carId, Long departmentId){
        var department = departmentRepository.findById(departmentId);
        var car = carRepository.findById(carId);
        if(car.isPresent() && department.isPresent()){
            var c = car.get();
            var d = department.get();
            d.getCars().add(c);
            departmentRepository.save(d);
        }
    }

    public void removeCarFromDepartment(Long carId, Long departmentId){
        var department = departmentRepository.findById(departmentId);
        var car = carRepository.findById(carId);
        if(car.isPresent() && department.isPresent()){
            var d = department.get();
            d.getCars().removeIf(carInList -> carInList.getId().equals(carId));
            departmentRepository.save(d);
        }
    }

    public void addEmployeeToDepartment(Long employeeId, Long departmentId){
        var employee = employeeRepository.findById(employeeId);
        var department = departmentRepository.findById(departmentId);
        if (employee.isPresent() && department.isPresent()){
            var e = employee.get();
            var d = department.get();
            d.getEmployees().add(e);
            departmentRepository.save(d);
        }
    }

    public void removeEmployeeFromDepartment(Long employeeId, Long departmentId){
        var department = departmentRepository.findById(departmentId);
        var employee = employeeRepository.findById(employeeId);
        if (employee.isPresent() && department.isPresent()){
            var d = department.get();
            d.getEmployees().removeIf(employeeInList -> employeeInList.getId().equals(employeeId));
            departmentRepository.save(d);
        }
    }

    public boolean isEmployeeInDepartment(Long employeeId, Long departmentId){
        var department = departmentRepository.findById(departmentId);
        var employee = employeeRepository.findById(employeeId);
        if (department.isPresent() && employee.isPresent()){
            var d = department.get();
            var e = employee.get();
            return d.getEmployees().stream().anyMatch(employeeInList -> employeeInList.getId().equals(e.getId()));
        }
        return false;
    }

    public boolean isEmployeeInAnyDepartment(Long employeeId){
        return departmentRepository.findAll().stream()
                .anyMatch(department -> department.getEmployees().stream().anyMatch(employee -> employee.getId().equals(employeeId)));
    }

    public boolean isCarInDepartment(Long carId, Long departmentId){
        var department = departmentRepository.findById(departmentId);
        var car = carRepository.findById(carId);
        if (department.isPresent() && car.isPresent()){
            var d = department.get();
            var e = car.get();
            return d.getCars().stream().anyMatch(carInList -> carInList.getId().equals(e.getId()));
        }
        return false;
    }

    public boolean isCarInAnyDepartment(Long carId){
        return departmentRepository.findAll().stream()
                .anyMatch(department -> department.getCars().stream().anyMatch(car -> car.getId().equals(carId)));
    }

}
