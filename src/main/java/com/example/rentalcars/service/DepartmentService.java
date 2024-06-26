package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.DepartmentAdditionException;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public DepartmentModel addDepartment(DepartmentModel department) {
        try {
            DepartmentModel savedDepartment = departmentRepository.save(department);
            System.out.println("Oddział został dodany pomyślnie");
            return savedDepartment;
        } catch (Exception e) {
            throw new DepartmentAdditionException("Błąd podczas dodawania oddziału.", e);
        }
    }

    public List<DepartmentModel> getDepartmentList() {
        return departmentRepository.findAll();
    }

    public DepartmentModel findById(Long id) {
        Optional<DepartmentModel> departmentModel = departmentRepository.findById(id);
        return departmentModel.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono oddziału o id = " + id));
    }

    public void updateDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }

    public void deleteDepartment(DepartmentModel departmentModel){
        departmentRepository.delete(departmentModel);
    }

    public Set<EmployeeModel> getDepartmentEmployees(Long departmentId) {
        return getDepartmentList().stream()
                .filter(department -> department.getId() != null && department.getId().equals(departmentId))
                .map(DepartmentModel::getEmployees)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Optional<DepartmentModel> getDepartmentByEmployee(EmployeeModel employeeModel) {
        return getDepartmentList().stream()
            .filter(d -> d.getEmployees().contains(employeeModel))
            .findFirst();
    }

    public Long countEmployeesInDepartment(Long departmentId){
        return getDepartmentEmployees(departmentId).stream().count();
    }

    public List<CarModel> getAllCarsByDepartment(Long departmentId) {
        return getDepartmentList().stream()
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
        return getDepartmentList().stream()
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
        return getDepartmentList().stream()
                .anyMatch(department -> department.getCars().stream().anyMatch(car -> car.getId().equals(carId)));
    }

}
