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
@PageTitle("Pracownicy wg oddziałów")
@PermitAll
public class DepartmentsEmployeesView extends VerticalLayout {
    private final DepartmentService departmentService;
    Grid<EmployeeModel> grid = new Grid<>(EmployeeModel.class);
    ComboBox<DepartmentModel> departmentModelComboBox = new ComboBox<>();


    public DepartmentsEmployeesView(DepartmentService departmentService) {
        this.departmentService = departmentService;
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
        HorizontalLayout toolbar = new HorizontalLayout(departmentModelComboBox);
        departmentModelComboBox.setPlaceholder("Oddziały");
        departmentModelComboBox.setItems(departmentService.getDepartmentList1());
        departmentModelComboBox.setItemLabelGenerator(DepartmentModel::getCity);
        departmentModelComboBox.setClearButtonVisible(true);
        departmentModelComboBox.addValueChangeListener(e -> updateDepartmentEmployeeList());
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateDepartmentEmployeeList() {
        grid.setItems(departmentService.findById(departmentModelComboBox.getValue().getId()).getEmployees());
    }


}
