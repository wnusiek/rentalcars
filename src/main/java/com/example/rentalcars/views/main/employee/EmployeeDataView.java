package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "employeeDataView", layout = MainLayout.class)
@PageTitle("Moje dane")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
public class EmployeeDataView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final UserService userService;
    EmployeeDataForm form = new EmployeeDataForm();

    public EmployeeDataView(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
        setSizeFull();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(form);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new EmployeeDataForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveEmployee);
        form.addCloseListener(event -> closeEditor());
    }

    private void saveEmployee(EmployeeDataForm.SaveEvent event) {
        employeeService.saveEmployee(event.getEmployee());
        closeEditor();
    }

    private Component getToolbar() {
        Button editEmployeeButton = new Button("Edytuj swoje dane");
        EmployeeModel employee = employeeService.getEmployeeByUserName(userService.getNameOfLoggedUser());
        editEmployeeButton.addClickListener(e -> editEmployee(employee));
        HorizontalLayout toolbar = new HorizontalLayout(editEmployeeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void editEmployee(EmployeeModel employeeModel) {
        if (employeeModel == null) {
            closeEditor();
        } else {
            form.setEmployee(employeeModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
