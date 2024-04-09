package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "customerReservations", layout = MainLayout.class)
@PageTitle("Moje rezerwacje")
@Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_CUSTOMER", "ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
public class CustomerReservationsView extends VerticalLayout {

    private final ReservationService reservationService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class, false);
    ReservationModel reservationModel;

    public CustomerReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;
        setSizeFull();
        configureGrid();
        add(
                getToolbar(),
                getContent()
        );
        updateReservationList();
    }

    private Component getToolbar() {
        Button cancelReservationButton = new Button("Anuluj rezerwację");
        cancelReservationButton.addClickListener(e -> cancelReservation());

        HorizontalLayout toolbar = new HorizontalLayout(cancelReservationButton);
        return toolbar;
    }

    private void cancelReservation() {
        if (this.reservationModel == null){
            Notification.show("Zaznacz rezerwację, którą chcesz anulować").setPosition(Notification.Position.MIDDLE);
        } else {
            reservationService.cancelReservation(this.reservationModel);
            updateReservationList();
        }

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
        grid.addColumn("car.mark").setHeader("Marka");
        grid.addColumn("car.model").setHeader("Model");
        grid.addColumn("dateFrom").setHeader("Data od");
        grid.addColumn("dateTo").setHeader("Data do");
        grid.addColumn("price").setHeader("Koszt");
        grid.addColumn("receptionVenue.city").setHeader("Miejsce wypożyczenia");
        grid.addColumn("returnVenue.city").setHeader("Miejsce zwrotu");
        grid.addColumn("reservationStatus").setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> setCancel(event.getValue()));
    }

    private ReservationModel setCancel(ReservationModel reservationModel) {
            return this.reservationModel = reservationModel;
    }


}
