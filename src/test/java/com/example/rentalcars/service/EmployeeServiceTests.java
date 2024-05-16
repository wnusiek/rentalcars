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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private EmployeeModel employeeModel;

    @BeforeEach
    public void setup(){
        roleModel = new RoleModel(1L, "ADMIN");
        userModel = new UserModel(1L, "Bolek", "bolek123", "bolek@gmail.com", true, roleModel);
        employeeModel = new EmployeeModel(1l, "Janusz", "Biznesu", EmployeePosition.MANAGER, userModel);
    }

    @Test
    public void testGetEmployeeList_ReturnEmployeeList() {
        when(employeeRepository.findAll()).thenReturn(List.of(employeeModel, new EmployeeModel(), new EmployeeModel()));
        List<EmployeeModel> savedEmployeeList = employeeService.getEmployeeList();
        assertThat(savedEmployeeList.size()).isEqualTo(3);
    }

    @Test
    public void testGetEmployeeList_ReturnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        List<EmployeeModel> savedEmployeeList = employeeService.getEmployeeList();
        assertThat(savedEmployeeList).isEmpty();
    }

    @Test
    public void testSaveEmployee_EmployeeSaved() {
        when(employeeRepository.save(employeeModel)).thenReturn(employeeModel);
        EmployeeModel savedEmployee = employeeService.saveEmployee(employeeModel);
        assertThat(savedEmployee).isEqualTo(employeeModel);
    }

    @Test
    public void testSaveEmployee_ExceptionThrown() {
        when(employeeRepository.save(employeeModel)).thenThrow(new RuntimeException());
        assertThrows(EmployeeAdditionException.class, () -> employeeService.saveEmployee(employeeModel));
    }

    @Test
    public void testDeleteEmployee() {
        willDoNothing().given(employeeRepository).delete(employeeModel);
        employeeService.deleteEmployee(employeeModel);
        verify(employeeRepository, times(1)).delete(employeeModel);
    }

    @Test
    public void testGetEmployeeByUserName_ReturnEmployee() {
        EmployeeModel employeeModel1 = new EmployeeModel(2L, "Jola", "Patola", EmployeePosition.EMPLOYEE,
                                       new UserModel(2L, "mądrajola", "jądramola", "", true, roleModel));
        EmployeeModel employeeModel2 = new EmployeeModel(3L, "Grażyna", "Dajnawino", EmployeePosition.MANAGER,
                                       new UserModel(3L, "graża", "matkanapełnyetat", "", true, roleModel));

        when(employeeRepository.findAll()).thenReturn(List.of(employeeModel, employeeModel1, employeeModel2));
        EmployeeModel returnedEmployee1 = employeeService.getEmployeeByUserName("Bolek");
        EmployeeModel returnedEmployee2 = employeeService.getEmployeeByUserName("graża");
        EmployeeModel returnedEmployee3 = employeeService.getEmployeeByUserName("mądrajola");

        assertThat(returnedEmployee1).isNotNull();
        assertThat(returnedEmployee2).isNotNull();
        assertThat(returnedEmployee3).isNotNull();
        assertThat(returnedEmployee1.getUser().getName()).isEqualTo("Bolek");
        assertThat(returnedEmployee2.getUser().getName()).isEqualTo("graża");
        assertThat(returnedEmployee3.getUser().getName()).isEqualTo("mądrajola");
    }

    @Test void testGetEmployeeByUserName_ThrowException() {
        EmployeeModel employeeModel1 = new EmployeeModel(2L, "Jola", "Patola", EmployeePosition.EMPLOYEE,
                new UserModel(2L, "mądrajola", "jądramola", "", true, roleModel));
        EmployeeModel employeeModel2 = new EmployeeModel(3L, "Grażyna", "Dajnawino", EmployeePosition.MANAGER,
                new UserModel(3L, "graża", "matkanapełnyetat", "", true, roleModel));

        when(employeeRepository.findAll()).thenReturn(List.of(employeeModel, employeeModel1, employeeModel2));

        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserName("bolek"));
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserName("MĄDRAJOLA"));
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeByUserName("grażaa"));
        assertDoesNotThrow(() -> employeeService.getEmployeeByUserName("Bolek"));
        assertDoesNotThrow(() -> employeeService.getEmployeeByUserName("mądrajola"));
        assertDoesNotThrow(() -> employeeService.getEmployeeByUserName("graża"));
    }
}
