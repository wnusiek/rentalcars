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
    private DepartmentModel departmentModel0;
    private EmployeeModel employeeModel0;
    private CarModel carModel0;
    @BeforeEach
    public void setup() {
        Set<EmployeeModel> employeeModelSet = new HashSet<>();
        List<CarModel> carModelList = new ArrayList<>();
        employeeModel0 = new EmployeeModel(1L, "Janusz", "Biznesu", EmployeePosition.MANAGER, new UserModel());
        carModel0 = new CarModel(1L, "", "", BigDecimal.ONE, BigDecimal.ONE, BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "", CarStatus.AVAILABLE, "", 100, 2000);
        departmentModel0 = new DepartmentModel(1L, "Lublin", "Krakowskie Przedmieście 1", "123456789", employeeModelSet, carModelList);
    }

    @Test
    public void testAddDepartment_Added() {
        when(departmentRepository.save(departmentModel0)).thenReturn(departmentModel0);
        DepartmentModel savedDepartment = departmentService.addDepartment(departmentModel0);
        assertThat(savedDepartment).isNotNull().isEqualTo(departmentModel0);
    }

    @Test
    public void testAddDepartment_ExceptionThrown() {
        when(departmentRepository.save(departmentModel0)).thenThrow(new RuntimeException());
        assertThrows(DepartmentAdditionException.class, () -> departmentService.addDepartment(departmentModel0));
    }

    @Test
    public void testGetDepartmentList_ReturnDepartmentList() {
        DepartmentModel departmentModel1 = new DepartmentModel();
        when(departmentRepository.findAll()).thenReturn(List.of(departmentModel0, departmentModel1));
        List<DepartmentModel> savedDepartmentList = departmentService.getDepartmentList();
        assertThat(savedDepartmentList.size()).isEqualTo(2);
    }

    @Test
    public void testGetDepartmentList_ReturnEmptyList() {
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());
        List<DepartmentModel> savedDepartmentList = departmentService.getDepartmentList();
        assertThat(savedDepartmentList).isEmpty();
    }

    @Test
    public void testFindById_ReturnDepartmentModel() {
        Long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(departmentModel0));
        DepartmentModel savedDepartment = departmentService.findById(id);
        assertThat(savedDepartment).isEqualTo(departmentModel0);
    }

    @Test
    public void testFindById_ExceptionThrown() {
        Long id = 1L;
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> departmentService.findById(id));
    }

    @Test
    public void testDeleteDepartmentById_thenNothing() {
        Long id = 1L;
        willDoNothing().given(departmentRepository).deleteById(id);
        departmentService.deleteDepartmentById(id);
        verify(departmentRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteDepartment_thenNothing() {
        willDoNothing().given(departmentRepository).delete(departmentModel0);
        departmentService.deleteDepartment(departmentModel0);
        verify(departmentRepository, times(1)).delete(departmentModel0);
    }

    @Test
    public void testGetDepartmentEmployees_returnDepartmentEmployeeList() {
        Long departmentId = 1L;
        EmployeeModel employee1 = employeeModel0;
        EmployeeModel employee2 = new EmployeeModel();
        departmentModel0.getEmployees().add(employee1);
        departmentModel0.getEmployees().add(employee2);
        when(departmentRepository.findAll()).thenReturn(List.of(departmentModel0, new DepartmentModel(), new DepartmentModel()));

        Set<EmployeeModel> savedDepartmentEmployeesSet = departmentService.getDepartmentEmployees(departmentId);

        assertThat(savedDepartmentEmployeesSet.size()).isEqualTo(2);
        assertThat(savedDepartmentEmployeesSet).contains(employee1);
        assertThat(savedDepartmentEmployeesSet).contains(employee2);
    }

    @Test
    public void testGetDepartmentEmployees_whenWrongDepartmentId_returnEmptyList() {
        Long id = 2L;
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());
        Set<EmployeeModel> savedDepartmentEmployeeList = departmentService.getDepartmentEmployees(id);
        assertThat(savedDepartmentEmployeeList).isEmpty();
    }

    @Test
    public void testGetDepartmentByEmployee_ReturnDepartment() {
        departmentModel0.getEmployees().add(employeeModel0);
        departmentModel0.getEmployees().add(new EmployeeModel());
        when(departmentService.getDepartmentList()).thenReturn(List.of(departmentModel0, new DepartmentModel()));
        DepartmentModel savedDepartment = departmentService.getDepartmentByEmployee(employeeModel0);
        assertThat(savedDepartment).isEqualTo(departmentModel0);
        assertThat(savedDepartment.getEmployees()).contains(employeeModel0);
        assertThat(savedDepartment.getEmployees().size()).isEqualTo(2);
    }

    @Test
    public void testGetDepartmentByEmployee_ExceptionThrown() {
        when(departmentService.getDepartmentList()).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> departmentService.getDepartmentByEmployee(employeeModel0));
    }

    @Test
    public void getAllCarsByDepartmentId_ReturnDepartmentCarList() {
        departmentModel0.getCars().add(carModel0);
        DepartmentModel departmentModel1 = new DepartmentModel(2L, "", "", "", Collections.emptySet(), List.of(new CarModel(), new CarModel()));
        Long departmentId = 1L;
        when(departmentService.getDepartmentList()).thenReturn(List.of(departmentModel0, departmentModel1));
        List<CarModel> savedCarList = departmentService.getAllCarsByDepartment(departmentId);
        assertThat(savedCarList.size()).isEqualTo(1);
        assertThat(savedCarList).contains(carModel0);
    }

    @Test
    public void getAllCarsByDepartmentId_ReturnEmptyList() {
        Long id = 1L;
        when(departmentService.getDepartmentList()).thenReturn(Collections.emptyList());
        List<CarModel> savedCarList = departmentService.getAllCarsByDepartment(id);
        assertThat(savedCarList).isEmpty();
    }

    @Test
    public void testAddCarToDepartment_Success() {
        Long carId = 1L;
        Long departmentId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        departmentService.addCarToDepartment(carId, departmentId);

        verify(departmentRepository).save(departmentModel0);
        assertThat(departmentModel0.getCars()).contains(carModel0);
    }

    @Test
    public void testAddCarToDepartment_CarNotFound() {
        Long carId = 1L;
        Long departmentId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        departmentService.addCarToDepartment(carId, departmentId);

        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testAddCarToDepartment_DepartmentNotFound() {
        Long carId = 1L;
        Long departmentId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        departmentService.addCarToDepartment(carId, departmentId);

        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveCarFromDepartment_Success() {
        Long carId = 1L;
        Long departmentId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));
        departmentService.removeCarFromDepartment(carId, departmentId);
        verify(departmentRepository, times(1)).save(departmentModel0);
        assertThat(departmentModel0.getCars().contains(carModel0)).isFalse();

    }

    @Test
    public void testRemoveCarFromDepartment_CarNotFound() {
        Long carId = 1L;
        Long departmentId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));
        departmentService.removeCarFromDepartment(carId, departmentId);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveCarFromDepartment_DepartmentNotFound() {
        Long carId = 1L;
        Long departmentId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        departmentService.removeCarFromDepartment(carId, departmentId);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testAddEmployeeToDepartment_Success() {
        Long employeeId = 1L;
        Long departmentId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        departmentService.addEmployeeToDepartment(employeeId, departmentId);

        verify(departmentRepository).save(departmentModel0);
        assertThat(departmentModel0.getEmployees()).contains(employeeModel0);
    }

    @Test
    public void testAddEmployeeToDepartment_EmployeeNotFound() {
        Long employeeId = 1L;
        Long departmentId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        departmentService.addEmployeeToDepartment(employeeId, departmentId);

        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testAddEmployeeToDepartment_DepartmentNotFound() {
        Long employeeId = 1L;
        Long departmentId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        departmentService.addEmployeeToDepartment(employeeId, departmentId);

        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveEmployeeFromDepartment_Success() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));
        departmentService.removeEmployeeFromDepartment(employeeId, departmentId);
        verify(departmentRepository, times(1)).save(departmentModel0);
        assertThat(departmentModel0.getEmployees().contains(employeeModel0)).isFalse();
    }

    @Test
    public void testRemoveEmployeeFromDepartment_EmployeeNotFound() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));
        departmentService.removeEmployeeFromDepartment(employeeId, departmentId);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testRemoveEmployeeFromDepartment_DepartmentNotFound() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        departmentService.removeEmployeeFromDepartment(employeeId, departmentId);
        verify(departmentRepository, never()).save(any(DepartmentModel.class));
    }

    @Test
    public void testIsEmployeeInDepartment_ReturnTrue() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        departmentModel0.getEmployees().add(employeeModel0);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        Boolean result = departmentService.isEmployeeInDepartment(employeeId, departmentId);

        assertThat(result).isTrue();
    }

    @Test
    public void testIsEmployeeInDepartment_ReturnFalse() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        Boolean result = departmentService.isEmployeeInDepartment(employeeId, departmentId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsEmployeeInDepartment_EmployeeNotFound() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        departmentModel0.getEmployees().add(employeeModel0);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        Boolean result = departmentService.isEmployeeInDepartment(employeeId, departmentId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsEmployeeInDepartment_DepartmentNotFound() {
        Long employeeId = 1L;
        Long departmentId = 1L;
        departmentModel0.getEmployees().add(employeeModel0);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        Boolean result = departmentService.isEmployeeInDepartment(employeeId, departmentId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInDepartment_ReturnTrue() {
        Long carId = 1L;
        Long departmentId = 1L;
        departmentModel0.getCars().add(carModel0);
        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        Boolean result = departmentService.isCarInDepartment(carId, departmentId);

        assertThat(result).isTrue();
    }

    @Test
    public void testIsCarInDepartment_ReturnFalse() {
        Long carId = 1L;
        Long departmentId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        Boolean result = departmentService.isCarInDepartment(carId, departmentId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInDepartment_CarNotFound() {
        Long carId = 1L;
        Long departmentId = 1L;
        departmentModel0.getCars().add(carModel0);
        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(departmentModel0));

        Boolean result = departmentService.isCarInDepartment(carId, departmentId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInDepartment_DepartmentNotFound() {
        Long carId = 1L;
        Long departmentId = 1L;
        departmentModel0.getCars().add(carModel0);
        when(carRepository.findById(carId)).thenReturn(Optional.of(carModel0));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        Boolean result = departmentService.isCarInDepartment(carId, departmentId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsEmployeeInAnyDepartment_ReturnTrue() {
        Long employeeId = 1L;
        departmentModel0.getEmployees().add(employeeModel0);
        when(departmentRepository.findAll()).thenReturn(List.of(departmentModel0));

        Boolean result = departmentService.isEmployeeInAnyDepartment(employeeId);

        assertThat(result).isTrue();
    }

    @Test
    public void testIsEmployeeInAnyDepartment_ReturnFalse() {
        Long employeeId = 1L;
        when(departmentRepository.findAll()).thenReturn(List.of(departmentModel0));

        Boolean result = departmentService.isEmployeeInAnyDepartment(employeeId);

        assertThat(result).isFalse();
    }

    @Test
    public void testIsCarInAnyDepartment_ReturnTrue() {
        Long carId = 1L;
        departmentModel0.getCars().add(carModel0);
        when(departmentRepository.findAll()).thenReturn(List.of(departmentModel0));

        Boolean result = departmentService.isCarInAnyDepartment(carId);

        assertThat(result).isTrue();
    }

    @Test
    public void testIsCarInAnyDepartment_ReturnFalse() {
        Long carId = 1L;
        when(departmentRepository.findAll()).thenReturn(List.of(departmentModel0));

        Boolean result = departmentService.isCarInAnyDepartment(carId);

        assertThat(result).isFalse();
    }

    @Test
    public void testCountEmployeesInDepartmentWithoutEmployees() {
        Long departmentId = 1L;
        assertThat(departmentService.countEmployeesInDepartment(departmentId)).isEqualTo(0);
    }

    @Test
    public void testCountEmployeesInDepartmentWithEmployees() {
        Long departmentId = 1L;
        Long employeeId = 1L;
        departmentService.addEmployeeToDepartment(employeeId, departmentId);
        assertThat(departmentService.countEmployeesInDepartment(departmentId)).isEqualTo(0);
    }
}