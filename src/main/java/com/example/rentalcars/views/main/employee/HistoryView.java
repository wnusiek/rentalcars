package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.RentalService;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.service.ReturnService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "historyView", layout = MainLayout.class)
@PageTitle("Rezerwacje, wypożyczenia i zwroty")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
public class HistoryView extends VerticalLayout {
    private final ReservationService reservationService;
    private final RentalService rentalService;
    private final ReturnService returnService;

    Grid<ReservationModel> reservationGrid = new Grid<>(ReservationModel.class, false);
    Grid<RentalModel> rentalGrid = new Grid<>(RentalModel.class, false);
    Grid<ReturnModel> returnGrid = new Grid<>(ReturnModel.class, false);
    TextField filter = new TextField();
    DatePicker date = new DatePicker("Data");

    public HistoryView(ReservationService reservationService, RentalService rentalService, ReturnService returnService) {
        this.reservationService = reservationService;
        this.rentalService = rentalService;
        this.returnService = returnService;

        setSizeFull();
        configureReservationGrid();
        configureRentalGrid();
        configureReturnGrid();

        add(
                getToolbar(),
                new H2("Rezerwacje"),
                reservationGrid,
                new H2("Wypożyczenia"),
                rentalGrid,
                new H2("Zwroty"),
                returnGrid
        );
        updateLists();
    }

    private HorizontalLayout getToolbar() {
        filter.setPlaceholder("Filtrowanie po nazwisku klienta...");
        filter.setClearButtonVisible(true);
        filter.setWidth("300px");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateLists());

        var toolbar = new HorizontalLayout(filter, date);
        return toolbar;
    }

    private void updateLists() {
//        reservationGrid.setItems(reservationService.getReservationListByCustomerLastName(filter.getValue()));
        reservationGrid.setItems((reservationService.getReservationListWithFilters(filter.getValue(), date.getValue())));
        rentalGrid.setItems(rentalService.getRentalListByCustomerLastName(filter.getValue()));
        returnGrid.setItems(returnService.getReturnListByCustomerLastName(filter.getValue()));
    }

    private void configureReservationGrid() {
        reservationGrid.setSizeFull();
        reservationGrid.addColumn("car.mark").setHeader("Marka");
        reservationGrid.addColumn("car.model").setHeader("Model");
        reservationGrid.addColumn("dateFrom").setHeader("Data od");
        reservationGrid.addColumn("dateTo").setHeader("Data do");
        reservationGrid.addColumn("price").setHeader("Cena");
        reservationGrid.addColumn("receptionVenue.city").setHeader("Miejsce odbioru");
        reservationGrid.addColumn("returnVenue.city").setHeader("Miejsce zwrotu");
        reservationGrid.addColumn("customer.firstName").setHeader("Imię klienta");
        reservationGrid.addColumn("customer.lastName").setHeader("Nazwisko klienta");
        reservationGrid.addColumn("reservationStatus").setHeader("Status rezerwacji");
        reservationGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureRentalGrid(){
        rentalGrid.setSizeFull();
        rentalGrid.addColumn("employee.firstName").setHeader("Imię pracownika");
        rentalGrid.addColumn("employee.lastName").setHeader("Nazwisko pracownika");
        rentalGrid.addColumn("reservation.car.mark").setHeader("Marka samochodu");
        rentalGrid.addColumn("reservation.car.model").setHeader("Model samochodu");
        rentalGrid.addColumn("dateOfRental").setHeader("Data wypożyczenia");
        rentalGrid.addColumn("reservation.customer.firstName").setHeader("Imię klienta");
        rentalGrid.addColumn("reservation.customer.lastName").setHeader("Nazwisko klienta");
        rentalGrid.addColumn("comments").setHeader("Komentarz");
        rentalGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureReturnGrid(){
        returnGrid.setSizeFull();
        returnGrid.addColumn("employee.firstName").setHeader("Imię pracownika");
        returnGrid.addColumn("employee.lastName").setHeader("Nazwisko pracownika");
        returnGrid.addColumn("reservation.car.mark").setHeader("Marka samochodu");
        returnGrid.addColumn("reservation.car.model").setHeader("Model samochodu");
        returnGrid.addColumn("dateOfReturn").setHeader("Data zwrotu");
        returnGrid.addColumn("reservation.customer.firstName").setHeader("Imię Klienta");
        returnGrid.addColumn("reservation.customer.lastName").setHeader("Nazwisko klienta");
        returnGrid.addColumn("comments").setHeader("Komentarz");
        returnGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
