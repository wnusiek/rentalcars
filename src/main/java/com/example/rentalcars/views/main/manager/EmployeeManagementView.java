package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "employeeManagementView", layout = MainLayout.class)
@PageTitle("Pracownicy")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
public class EmployeeManagementView extends VerticalLayout {

    private final EmployeeService employeeService;
    private final UserService userService;
    Grid<EmployeeModel> grid = new Grid<>(EmployeeModel.class, false);
    EmployeeManagementForm form;

    public EmployeeManagementView(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
        addClassName("employees-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateEmployeeList();
        closeEditor();
    }

    private void closeEditor() {
        form.setEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new EmployeeManagementForm(userService.getAllUsers());
        form.setWidth("25em");

        form.addSaveListener(this::saveEmployee);
        form.addDeleteListener(this::deleteEmployee);
        form.addCloseListener(event -> closeEditor());
    }

    private void deleteEmployee(EmployeeManagementForm.DeleteEvent event) {
        employeeService.deleteEmployee(event.getEmployee());
        updateEmployeeList();
        closeEditor();
    }

    private void saveEmployee(EmployeeManagementForm.SaveEvent event){
        employeeService.saveEmployee(event.getEmployee());
        updateEmployeeList();
        closeEditor();
    }

    private Component getToolbar(){
        Button addEmployeeButton = new Button("Dodaj pracownika");
        addEmployeeButton.addClickListener(e->addEmployee());

        HorizontalLayout toolbar = new HorizontalLayout(addEmployeeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addEmployee() {
        grid.asSingleSelect().clear();
        editEmployee(new EmployeeModel());
    }

    private void updateEmployeeList() {
        grid.setItems(employeeService.getEmployeeList());
    }

    private void configureGrid() {
        grid.addClassNames("employees-grid");
        grid.setSizeFull();
        grid.addColumn("firstName").setHeader("Imię");
        grid.addColumn("lastName").setHeader("Nazwisko");
        grid.addColumn("position").setHeader("Stanowisko");
//        grid.addColumn("user.name").setHeader("Nazwa użytkownika");
//        grid.addColumn("user.email").setHeader("Email");
//        grid.addColumn("user.state").setHeader("Czy aktywny");
//        grid.addColumn("user.role.name").setHeader("Rola użytkownika");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editEmployee(event.getValue()));
    }

    private void editEmployee(EmployeeModel employeeModel) {
        if(employeeModel == null){
            closeEditor();
        }else {
            form.setEmployee(employeeModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
