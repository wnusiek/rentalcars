package com.example.rentalcars.views.main;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.service.RentalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "rentals", layout = MainLayout.class)
@PageTitle("Lista wypożyczeń")
@PermitAll
public class RentalView extends VerticalLayout {

    private final RentalService rentalService;
    Grid<RentalModel> grid = new Grid<>(RentalModel.class);

    public RentalView(RentalService rentalService) {
        this.rentalService = rentalService;
        addClassName("rentals-view");
        setSizeFull();
        configureGrid();
        add(
                getContent()
        );
        updateRentalList();
    }

    private Component getContent() {
//        HorizontalLayout content = new HorizontalLayout(grid, form);
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2,grid);
//        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;    }
    private void updateRentalList(){
        grid.setItems(rentalService.getRentalList());
    }
    private void configureGrid(){
        grid.addClassNames("rentals-grid");
        grid.setSizeFull();
        grid.setColumns("employee.firstName", "employee.lastName", "dateOfRental", "reservation.customer.firstName", "reservation.customer.lastName", "comments");
    }
}
