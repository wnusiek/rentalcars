package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "reservations", layout = MainLayout.class)
@PageTitle("Lista rezerwacji")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class ReservationsView extends VerticalLayout {

    private final ReservationService reservationService;

    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);

    public ReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;
        addClassName("reservations-view");
        setSizeFull();
        configureGrid();
        add(
                getContent()
        );
        updateReservationList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setSizeFull();
        return content;
    }

    private void updateReservationList() {
        grid.setItems(reservationService.getReservationList());
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue.city", "returnVenue.city", "customer.firstName", "customer.lastName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }



}
