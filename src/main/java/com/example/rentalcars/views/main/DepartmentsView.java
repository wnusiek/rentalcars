package com.example.rentalcars.views.main;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.DepartmentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "departments", layout = MainLayout.class)
@PageTitle("Lista oddziałów")
@PermitAll
public class DepartmentsView extends VerticalLayout {

    private final DepartmentService departmentService;
    Grid<DepartmentModel> grid = new Grid<>(DepartmentModel.class);

    public DepartmentsView(DepartmentService departmentService) {
        this.departmentService = departmentService;
        addClassName("departments-view");
        setSizeFull();
        configureGrid();
        add(
                getContent()
        );
        updateDepartmentList();
    }

    private Component getContent() {
//        HorizontalLayout content = new HorizontalLayout(grid, form);
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2,grid);
//        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateDepartmentList(){
        grid.setItems(departmentService.getDepartmentList1());
    }

    private void configureGrid(){
        grid.addClassNames("departments-grid");
        grid.setSizeFull();
        grid.setColumns("city", "address");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }
}
