package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "customerReservations")
@PageTitle("Moje rezerwacje")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class CustomerReservationsView extends VerticalLayout {
    private final ReservationService reservationService;

    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);

    public CustomerReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;
        setSizeFull();
        configureGrid();
        add(
                getContent()
        );
        updateReservationList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateReservationList() {
        grid.setItems(reservationService.getReservationListLoggedUser());
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue.city", "returnVenue.city");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }



}
