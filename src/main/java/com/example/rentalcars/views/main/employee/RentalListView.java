package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.service.RentalService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "rentallist", layout = MainLayout.class)
@PageTitle("Lista wypożyczeń")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class RentalListView extends VerticalLayout {

    private final RentalService rentalService;
    Grid<RentalModel> rentalGrid = new Grid<>(RentalModel.class, false);

    public RentalListView(RentalService rentalService) {
        this.rentalService = rentalService;
        setSizeFull();
        configureGrid();
        add(rentalGrid);
        rentalGrid.setItems(rentalService.getRentalList());
    }

    private void configureGrid(){
        rentalGrid.setSizeFull();
        rentalGrid.addColumn("employee.firstName").setHeader("Imię pracownika");
        rentalGrid.addColumn("employee.lastName").setHeader("Nazwisko pracownika");
        rentalGrid.addColumn("dateOfRental").setHeader("Data wypożyczenia");
        rentalGrid.addColumn("reservation.customer.firstName").setHeader("Imię klienta");
        rentalGrid.addColumn("reservation.customer.lastName").setHeader("Nazwisko klienta");
        rentalGrid.addColumn("comments").setHeader("Komentarz");
        rentalGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
