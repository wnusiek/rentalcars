package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.DepartmentAdditionException;
import com.example.rentalcars.enums.*;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTests {
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private DepartmentService departmentService;
    private DepartmentModel departmentModel1;
    private DepartmentModel departmentModel2;
    private DepartmentModel departmentModel3;
    private EmployeeModel employeeModel1;
    private EmployeeModel employeeModel2;
    private CarModel carModel1;
    private Long departmentId1;
    private Long employeeId1;
    private Long carId1;

    @BeforeEach
    public void setup() {
        carId1 = 1L;
        employeeId1 = 1L;
        departmentId1 = 1L;

        employeeModel1 = new EmployeeModel(employeeId1, "Janusz", "Biznesu", EmployeePosition.MANAGER, new UserModel());
        employeeModel2 = new EmployeeModel();
        employeeModel2.setId(2L);

        carModel1 = new CarModel(carId1, "", "", BigDecimal.ONE, BigDecimal.ONE, BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "", CarStatus.AVAILABLE, "", 100, 2000, "");

        Set<EmployeeModel> employeeModelSet = new HashSet<>();
        employeeModelSet.add(employeeModel1);
        List<CarModel> carModelList = new ArrayList<>();
        carModelList.add(carModel1);

        departmentModel1 = new DepartmentModel(departmentId1, "Lublin", "Krakowskie Przedmieście 1", "123456789", employeeModelSet, carModelList);
        departmentModel2 = new DepartmentModel();
        departmentModel3 = new DepartmentModel();
        departmentModel2.setId(2L);
        departmentModel3.setId(3L);
        departmentModel2.setEmployees(new HashSet<>());
        departmentModel3.setEmployees(new HashSet<>());
    }

    @Test
    public void testAddDepartment_Added() {
        given(departmentRepository.save(departmentModel1)).willReturn(departmentModel1);
        var savedDepartment = departmentService.addDepartment(departmentModel1);
        assertThat(savedDepartment).isNotNull().isEqualTo(departmentModel1);
    }

    @Test
    public void testAddDepartment_ExceptionThrown() {
        given(departmentRepository.save(departmentModel1)).willThrow(new RuntimeException());
        assertThrows(DepartmentAdditionException.class, () -> departmentService.addDepartment(departmentModel1));
    }

    @Test
    public void testGetDepartmentList_ReturnDepartmentList() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel1, departmentModel2, departmentModel3));
        var savedDepartmentList = departmentService.getDepartmentList();
        assertThat(savedDepartmentList.size()).isEqualTo(3);
    }

    @Test
    public void testGetDepartmentList_ReturnEmptyList() {
        given(departmentRepository.findAll()).willReturn(Collections.emptyList());
        var savedDepartmentList = departmentService.getDepartmentList();
        assertThat(savedDepartmentList).isEmpty();
    }

    @Test
    public void testFindById_ReturnDepartmentModel() {
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        var savedDepartment = departmentService.findById(departmentId1);
        assertThat(savedDepartment).isEqualTo(departmentModel1);
    }

    @Test
    public void testFindById_ExceptionThrown() {
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.findById(departmentId1));
    }

    @Test
    public void testUpdateDepartment_Success() {
        given(departmentRepository.save(departmentModel1)).willReturn(departmentModel1);
        departmentService.updateDepartment(departmentModel1);
        verify(departmentRepository, times(1)).save(departmentModel1);
    }

    @Test
    public void testDeleteDepartmentById_Success() {
        willDoNothing().given(departmentRepository).deleteById(departmentId1);
        departmentService.deleteDepartmentById(departmentId1);
        verify(departmentRepository, times(1)).deleteById(departmentId1);
    }

    @Test
    public void testDeleteDepartment_Success() {
        willDoNothing().given(departmentRepository).delete(departmentModel1);
        departmentService.deleteDepartment(departmentModel1);
        verify(departmentRepository, times(1)).delete(departmentModel1);
    }

    @Test
    public void testGetDepartmentEmployees_returnDepartmentEmployeeList() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel1, departmentModel2, departmentModel3));
        var savedDepartmentEmployeesSet = departmentService.getDepartmentEmployees(departmentId1);
        assertThat(savedDepartmentEmployeesSet).containsExactly(employeeModel1);
    }

    @Test
    public void testGetDepartmentEmployees_whenWrongDepartmentId_returnEmptyList() {
        given(departmentRepository.findAll()).willReturn(Collections.emptyList());
        var savedDepartmentEmployeeList = departmentService.getDepartmentEmployees(departmentId1);
        assertThat(savedDepartmentEmployeeList).isEmpty();
    }

    @Test
    public void testGetDepartmentByEmployee_ReturnDepartment() {
        given(departmentService.getDepartmentList()).willReturn(List.of(departmentModel1, departmentModel2));
        var savedDepartment = departmentService.getDepartmentByEmployee(employeeModel1);
        assertThat(savedDepartment).isPresent();
        assertThat(savedDepartment.get()).isEqualTo(departmentModel1);
        assertThat(savedDepartment.get().getEmployees()).containsExactly(employeeModel1);
    }

    @Test
    public void testGetDepartmentByEmployee_ExceptionThrown() {
        when(departmentService.getDepartmentList()).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> departmentService.getDepartmentByEmployee(employeeModel1));
    }

    @Test
    public void getAllCarsByDepartmentId_ReturnDepartmentCarList() {
        given(departmentService.getDepartmentList()).willReturn(List.of(departmentModel1, departmentModel2));
        var savedCarList = departmentService.getAllCarsByDepartment(departmentId1);
        assertThat(savedCarList).containsExactly(carModel1);
    }

    @Test
    public void getAllCarsByDepartmentId_ReturnEmptyList() {
        given(departmentService.getDepartmentList()).willReturn(Collections.emptyList());
        var savedCarList = departmentService.getAllCarsByDepartment(departmentId1);
        assertThat(savedCarList).isEmpty();
    }

    @Test
    public void testAddCarToDepartment_Success() {
        Long departmentId2 = 2L;
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId2)).willReturn(Optional.of(departmentModel2));
        departmentService.addCarToDepartment(carId1, departmentId2);
        verify(departmentRepository).save(departmentModel2);
        assertThat(departmentModel2.getCars()).contains(carModel1);
    }

    @Test
    public void testAddCarToDepartment_CarNotFound() {
        given(carRepository.findById(carId1)).willReturn(Optional.empty());
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.addCarToDepartment(carId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testAddCarToDepartment_DepartmentNotFound() {
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        departmentService.addCarToDepartment(carId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveCarFromDepartment_Success() {
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.removeCarFromDepartment(carId1, departmentId1);
        verify(departmentRepository, times(1)).save(departmentModel1);
        assertThat(departmentModel1.getCars().contains(carModel1)).isFalse();
    }

    @Test
    public void testRemoveCarFromDepartment_CarNotFound() {
        given(carRepository.findById(carId1)).willReturn(Optional.empty());
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.removeCarFromDepartment(carId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveCarFromDepartment_DepartmentNotFound() {
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        departmentService.removeCarFromDepartment(carId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testAddEmployeeToDepartment_Success() {
        Long employeeId2 = 2L;
        given(employeeRepository.findById(employeeId2)).willReturn(Optional.of(employeeModel2));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.addEmployeeToDepartment(employeeId2, departmentId1);
        verify(departmentRepository).save(departmentModel1);
        assertThat(departmentModel1.getEmployees()).contains(employeeModel1, employeeModel2);
    }

    @Test
    public void testAddEmployeeToDepartment_EmployeeNotFound() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.empty());
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.addEmployeeToDepartment(employeeId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testAddEmployeeToDepartment_DepartmentNotFound() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        departmentService.addEmployeeToDepartment(employeeId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveEmployeeFromDepartment_Success() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.removeEmployeeFromDepartment(employeeId1, departmentId1);
        verify(departmentRepository, times(1)).save(departmentModel1);
        assertThat(departmentModel1.getEmployees().contains(employeeModel1)).isFalse();
    }

    @Test
    public void testRemoveEmployeeFromDepartment_EmployeeNotFound() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.empty());
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        departmentService.removeEmployeeFromDepartment(employeeId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveEmployeeFromDepartment_DepartmentNotFound() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        departmentService.removeEmployeeFromDepartment(employeeId1, departmentId1);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testIsEmployeeInDepartment_ReturnTrue() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        var result = departmentService.isEmployeeInDepartment(employeeId1, departmentId1);
        assertThat(result).isTrue();
    }

    @Test
    public void testIsEmployeeInDepartment_ReturnFalse() {
        Long departmentId2 = 2L;
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        given(departmentRepository.findById(departmentId2)).willReturn(Optional.of(departmentModel2));
        var result = departmentService.isEmployeeInDepartment(employeeId1, departmentId2);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsEmployeeInDepartment_EmployeeNotFound() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.empty());
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        var result = departmentService.isEmployeeInDepartment(employeeId1, departmentId1);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsEmployeeInDepartment_DepartmentNotFound() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        var result = departmentService.isEmployeeInDepartment(employeeId1, departmentId1);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInDepartment_ReturnTrue() {
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        var result = departmentService.isCarInDepartment(carId1, departmentId1);
        assertThat(result).isTrue();
    }

    @Test
    public void testIsCarInDepartment_ReturnFalse() {
        Long departmentId2 = 2L;
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId2)).willReturn(Optional.of(departmentModel2));
        var result = departmentService.isCarInDepartment(carId1, departmentId2);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInDepartment_CarNotFound() {
        given(carRepository.findById(carId1)).willReturn(Optional.empty());
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.of(departmentModel1));
        var result = departmentService.isCarInDepartment(carId1, departmentId1);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInDepartment_DepartmentNotFound() {
        given(carRepository.findById(carId1)).willReturn(Optional.of(carModel1));
        given(departmentRepository.findById(departmentId1)).willReturn(Optional.empty());
        var result = departmentService.isCarInDepartment(carId1, departmentId1);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsEmployeeInAnyDepartment_ReturnTrue() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel1, departmentModel2, departmentModel3));
        var result = departmentService.isEmployeeInAnyDepartment(employeeId1);
        assertThat(result).isTrue();
    }

    @Test
    public void testIsEmployeeInAnyDepartment_ReturnFalse() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel2, departmentModel3));
        var result = departmentService.isEmployeeInAnyDepartment(employeeId1);
        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInAnyDepartment_ReturnTrue() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel1, departmentModel2, departmentModel3));
        var result = departmentService.isCarInAnyDepartment(carId1);
        assertThat(result).isTrue();
    }

    @Test
    public void testIsCarInAnyDepartment_ReturnFalse() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel2, departmentModel3));
        var result = departmentService.isCarInAnyDepartment(carId1);
        assertThat(result).isFalse();
    }

    @Test
    public void testCountEmployeesInDepartmentWithEmployees() {
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel1, departmentModel2, departmentModel3));
        var result = departmentService.countEmployeesInDepartment(departmentId1);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testCountEmployeesInDepartmentWithoutEmployees() {
        Long departmentId2 = 2L;
        given(departmentRepository.findAll()).willReturn(List.of(departmentModel2, departmentModel3));
        var result = departmentService.countEmployeesInDepartment(departmentId2);
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testCountEmployeesInNonexistentDepartment() {
        given(departmentRepository.findAll()).willReturn(Collections.emptyList());
        var result = departmentService.countEmployeesInDepartment(departmentId1);
        assertThat(result).isEqualTo(0);
    }
}