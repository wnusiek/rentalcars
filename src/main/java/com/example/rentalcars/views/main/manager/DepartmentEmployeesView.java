package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.Collections;

@Route(value = "departmentsEmployees", layout = MainLayout.class)
@PageTitle("Pracownicy oddziałów")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class DepartmentEmployeesView extends VerticalLayout {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    Grid<EmployeeModel> grid = new Grid<>(EmployeeModel.class);
    ComboBox<DepartmentModel> departmentModelComboBox = new ComboBox<>("Wybierz oddział: ");
    ComboBox<EmployeeModel> employeeComboBox = new ComboBox<>("Wybierz pracownika");
    Button addButton = new Button("Dodaj pracownika do oddziału");
    Button removeButton = new Button("Usuń pracownika z oddziału");


    public DepartmentEmployeesView(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        setSizeFull();
        configureGrid();
        add(
                getToolbar(),
                getContent()
        );
//        updateDepartmentEmployeeList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2,grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "position");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private Component getToolbar(){

        HorizontalLayout toolbar = new HorizontalLayout(departmentModelComboBox, employeeComboBox, addButton, removeButton);

        departmentModelComboBox.setPlaceholder("Oddziały");
        departmentModelComboBox.setItems(departmentService.getDepartmentList1());
        departmentModelComboBox.setItemLabelGenerator(DepartmentModel::getCity);
        departmentModelComboBox.setClearButtonVisible(true);
        departmentModelComboBox.addValueChangeListener(e -> updateDepartmentEmployeeList());
        employeeComboBox.setPlaceholder("Pracownicy");
        employeeComboBox.setItems(employeeService.getEmployeeList());
        employeeComboBox.setItemLabelGenerator(EmployeeModel::getName);
        employeeComboBox.setClearButtonVisible(true);

        addButton.setText("Dodaj do oddziału");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton.setText("Usuń z oddziału");
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addButton.addClickListener(event -> addToDepartment(employeeComboBox.getValue()));
        removeButton.addClickListener(event -> removeFromDepartment(employeeComboBox.getValue()));

        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
    }

    private void updateDepartmentEmployeeList() {
        if (departmentModelComboBox.getValue() == null){
            grid.setItems(Collections.emptyList());
        } else{
            grid.setItems(departmentService.findById(departmentModelComboBox.getValue().getId()).getEmployees());
        }
    }

    private void addToDepartment(EmployeeModel employeeModel){
        var departmentModel = departmentModelComboBox.getValue();
        if (employeeModel == null ){
            Notification.show("Wybierz pracownika").setPosition(Notification.Position.MIDDLE);
        } else if (departmentModelComboBox.getValue() == null) {
            Notification.show("Wybierz oddział").setPosition(Notification.Position.MIDDLE);
        } else {
            if (departmentService.isEmployeeInDepartment(employeeModel.getId(), departmentModel.getId())) {
                Notification.show("Pracownik jest już w tym oddziale").setPosition(Notification.Position.MIDDLE);
            } else if (departmentService.isEmployeeInAnyDepartment(employeeModel.getId())){
                Notification.show("Pracownik jest już w innym oddziale").setPosition(Notification.Position.MIDDLE);
            } else {
                departmentService.addEmployeeToDepartment(employeeModel.getId(), departmentModel.getId());
                updateDepartmentEmployeeList();
                Notification.show("Dodano pracownika").setPosition(Notification.Position.MIDDLE);
            }
        }

    }

    private void removeFromDepartment(EmployeeModel employeeModel){
        var departmentModel = departmentModelComboBox.getValue();
        if (employeeModel == null ){
            Notification.show("Wybierz pracownika").setPosition(Notification.Position.MIDDLE);
        }
        else if (departmentModel == null){
            Notification.show("Wybierz oddział").setPosition(Notification.Position.MIDDLE);
        }else {
            if (departmentService.isEmployeeInDepartment(employeeModel.getId(), departmentModel.getId())){
                departmentService.removeEmployeeFromDepartment(employeeModel.getId(), departmentModel.getId());
                updateDepartmentEmployeeList();
                Notification.show("Usunięto pracownika").setPosition(Notification.Position.MIDDLE);
            }else {
                Notification.show("Pracownika nie ma w tym oddziale").setPosition(Notification.Position.MIDDLE);
            }
        }
    }



}
