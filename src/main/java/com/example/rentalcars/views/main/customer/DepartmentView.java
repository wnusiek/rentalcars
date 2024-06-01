package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.views.main.MainLayout;
import com.example.rentalcars.views.main.manager.DepartmentManagementForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "departmentView", layout = MainLayout.class)
@PageTitle("Oddziały")
@Secured("ROLE_CUSTOMER")
@RolesAllowed("ROLE_CUSTOMER")
public class DepartmentView extends VerticalLayout {
    private final DepartmentService departmentService;
    Grid<DepartmentModel> grid = new Grid<>(DepartmentModel.class, false);

    public DepartmentView(DepartmentService departmentService) {
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
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateDepartmentList(){
        grid.setItems(departmentService.getDepartmentList());
    }

    private void configureGrid(){
        grid.addClassNames("departments-grid");
        grid.setSizeFull();
        grid.addColumn("city").setHeader("Oddział");
        grid.addColumn("address").setHeader("Adres oddziału");
        grid.addColumn("phone").setHeader("Telefon");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
