package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.EmployeeAdditionException;
import com.example.rentalcars.enums.EmployeePosition;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;
    private RoleModel roleModel;
    private UserModel userModel;
    private EmployeeModel employeeModel1;
    private EmployeeModel employeeModel2;
    private EmployeeModel employeeModel3;
    private Long employeeId1;
    @BeforeEach
    public void setup(){
        employeeId1 = 1L;
        roleModel = new RoleModel(1L, "ADMIN");
        userModel = new UserModel(1L, "Bolek", "bolek123", "bolek@gmail.com", true, roleModel);
        employeeModel1 = new EmployeeModel(employeeId1, "Janusz", "Biznesu", EmployeePosition.MANAGER, userModel);

        employeeModel2 = new EmployeeModel(2L, "Jola", "Patola", EmployeePosition.EMPLOYEE,
                new UserModel(2L, "mądrajola", "jądramola", "", true, roleModel));

        employeeModel3 = new EmployeeModel(3L, "Grażyna", "Dajnawino", EmployeePosition.MANAGER,
                new UserModel(3L, "graża", "matkanapełnyetat", "", true, roleModel));

    }

    @Test
    public void testGetEmployeeList_ReturnEmployeeList() {
        given(employeeRepository.findAll()).willReturn(List.of(employeeModel1, new EmployeeModel(), new EmployeeModel()));
        var savedEmployeeList = employeeService.getEmployeeList();
        assertThat(savedEmployeeList.size()).isEqualTo(3);
    }

    @Test
    public void testGetEmployeeList_ReturnEmptyList() {
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        var savedEmployeeList = employeeService.getEmployeeList();
        assertThat(savedEmployeeList).isEmpty();
    }

    @Test
    public void testSaveEmployee_EmployeeSaved() {
        given(employeeRepository.save(employeeModel1)).willReturn(employeeModel1);
        var savedEmployee = employeeService.saveEmployee(employeeModel1);
        assertThat(savedEmployee).isEqualTo(employeeModel1);
    }

    @Test
    public void testSaveEmployee_ExceptionThrown() {
        given(employeeRepository.save(employeeModel1)).willThrow(new RuntimeException());
        assertThrows(EmployeeAdditionException.class, () -> employeeService.saveEmployee(employeeModel1));
    }

    @Test
    public void testDeleteEmployee() {
        willDoNothing().given(employeeRepository).delete(employeeModel1);
        employeeService.deleteEmployee(employeeModel1);
        verify(employeeRepository, times(1)).delete(employeeModel1);
    }

    @Test
    public void testGetEmployeeByUserName_ReturnEmployee() {
        given(employeeRepository.findAll()).willReturn(List.of(employeeModel1, employeeModel2, employeeModel3));
        EmployeeModel returnedEmployee1 = employeeService.getEmployeeByUserName("Bolek");
        EmployeeModel returnedEmployee2 = employeeService.getEmployeeByUserName("graża");
        EmployeeModel returnedEmployee3 = employeeService.getEmployeeByUserName("mądrajola");
        assertThat(returnedEmployee1.getUser().getName()).isEqualTo("Bolek");
        assertThat(returnedEmployee2.getUser().getName()).isEqualTo("graża");
        assertThat(returnedEmployee3.getUser().getName()).isEqualTo("mądrajola");
    }

    @Test void testGetEmployeeByUserName_ThrowException() {
        given(employeeRepository.findAll()).willReturn(List.of(employeeModel1, employeeModel2, employeeModel3));
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserName("bolek"));
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserName("MĄDRAJOLA"));
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserName("grażaa"));

    }

    @Test void testGetEmployeeByUserName_DoesNotThrowException() {
        given(employeeRepository.findAll()).willReturn(List.of(employeeModel1, employeeModel2, employeeModel3));
        assertDoesNotThrow(() -> employeeService.getEmployeeByUserName("Bolek"));
        assertDoesNotThrow(() -> employeeService.getEmployeeByUserName("mądrajola"));
        assertDoesNotThrow(() -> employeeService.getEmployeeByUserName("graża"));
    }
    @Test
    public void testFindById_ReturnEmployee() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.of(employeeModel1));
        var employee = employeeService.findById(employeeId1);
        assertThat(employee).isNotNull();
        assertThat(employee).isEqualTo(employeeModel1);
    }

    @Test
    public void testFindById_ThrowException() {
        given(employeeRepository.findById(employeeId1)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(employeeId1));
    }

}
