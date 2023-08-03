package com.example.rentalcars.views.main;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.DepartmentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "departmentsEmployees", layout = MainLayout.class)
@PageTitle("Pracownicy oddziałów")
@PermitAll
public class DepartmentsEmployeesView extends VerticalLayout {
    private final DepartmentService departmentService;
    Grid<EmployeeModel> grid = new Grid<>(EmployeeModel.class);
    ComboBox<DepartmentModel> departmentModelComboBox = new ComboBox<>("Oddziały");


    public DepartmentsEmployeesView(DepartmentService departmentService) {
        this.departmentService = departmentService;
        setSizeFull();
        configureGrid();
        add(
                getToolbar(),
                getContent()
        );
        updateEmployeeList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateEmployeeList() {
        grid.setItems(departmentService.getDepartmentEmployees(departmentModelComboBox.getValue().getId()));
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "position");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar(){
        departmentModelComboBox.setPlaceholder("Oddziały");
        departmentModelComboBox.setItems(departmentService.getDepartmentList1());
        departmentModelComboBox.setItemLabelGenerator(DepartmentModel::getCity);
        departmentModelComboBox.setClearButtonVisible(true);
        departmentModelComboBox.addValueChangeListener(e -> updateEmployeeList());

        var toolbar = new HorizontalLayout(departmentModelComboBox);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


}
