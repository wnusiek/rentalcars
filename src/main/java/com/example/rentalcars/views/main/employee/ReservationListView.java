package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "reservationlist", layout = MainLayout.class)
@PageTitle("Lista rezerwacji")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class ReservationListView extends VerticalLayout {

    private final ReservationService reservationService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class, false);

    public ReservationListView(ReservationService reservationService) {
        this.reservationService = reservationService;
        setSizeFull();
        configureGrid();
        add(grid);
        grid.setItems(reservationService.getReservationList());
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn("car.mark").setHeader("Marka");
        grid.addColumn("car.model").setHeader("Model");
        grid.addColumn("dateFrom").setHeader("Data od");
        grid.addColumn("dateTo").setHeader("Data do");
        grid.addColumn("price").setHeader("Cena");
        grid.addColumn("receptionVenue.city").setHeader("Miejsce odbioru");
        grid.addColumn("returnVenue.city").setHeader("Miejsce zwrotu");
        grid.addColumn("customer.firstName").setHeader("Imię klienta");
        grid.addColumn("customer.lastName").setHeader("Nazwisko klienta");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
