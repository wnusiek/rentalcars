package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.*;
import com.example.rentalcars.service.*;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private final DepartmentService departmentService;
    private final CustomerService customerService;
    private final RentalService rentalService;
    private final ReturnService returnService;

    Grid<ReservationModel> reservationGrid = new Grid<>(ReservationModel.class, false);
    Grid<RentalModel> rentalGrid = new Grid<>(RentalModel.class, false);
    Grid<ReturnModel> returnGrid = new Grid<>(ReturnModel.class, false);
    ComboBox<CustomerModel> customer = new ComboBox<>("Klient");
    DatePicker date = new DatePicker("Data");
    ComboBox<DepartmentModel> receptionVenue = new ComboBox<>("Oddział odbioru");
    ComboBox<ReservationStatus> reservationStatus = new ComboBox<>("Status rezerwacji");

    public HistoryView(ReservationService reservationService, DepartmentService departmentService, CustomerService customerService, RentalService rentalService, ReturnService returnService) {
        this.reservationService = reservationService;
        this.departmentService = departmentService;
        this.customerService = customerService;
        this.rentalService = rentalService;
        this.returnService = returnService;

        setSizeFull();
        configureReservationGrid();
        configureRentalGrid();
        configureReturnGrid();

        add(
                getToolbar(),
                getGrids()
        );
        updateLists();

    }

    private VerticalLayout getGrids() {
        Details reservations =  new Details("Rezerwacje", reservationGrid);
        reservations.setOpened(true);
        reservations.setWidthFull();
        reservations.setHeightFull();
        Details rentals = new Details("Wypożyczenia", rentalGrid);
        rentals.setOpened(true);
        rentals.setWidthFull();
        rentals.setHeightFull();
        Details returns = new Details("Zwroty", returnGrid);
        returns.setOpened(true);
        returns.setWidthFull();
        returns.setHeightFull();

        VerticalLayout grids = new VerticalLayout();
        grids.add(reservations, rentals, returns);
        return grids;
    }

    private HorizontalLayout getToolbar() {
        customer.setPlaceholder("Klient...");
        customer.setItems(customerService.getCustomerList());
        customer.setItemLabelGenerator(CustomerModel::getName);
        customer.setClearButtonVisible(true);
        customer.setWidth("300px");
        customer.addValueChangeListener(e -> updateLists());

        date.setPlaceholder("Rezerwacja od...");
        date.setClearButtonVisible(true);
        date.addValueChangeListener(e -> updateLists());

        receptionVenue.setPlaceholder("Wybierz oddział...");
        receptionVenue.setItems(departmentService.getDepartmentList());
        receptionVenue.setItemLabelGenerator(DepartmentModel::getCity);
        receptionVenue.setClearButtonVisible(true);
        receptionVenue.addValueChangeListener(e -> updateLists());

        reservationStatus.setPlaceholder("Wybierz status...");
        reservationStatus.setItems(ReservationStatus.values());
        reservationStatus.setClearButtonVisible(true);
        reservationStatus.setWidth("250px");
        reservationStatus.addValueChangeListener(e -> updateLists());
        var toolbar = new HorizontalLayout(customer, date, receptionVenue, reservationStatus);
        return toolbar;
    }

    private void updateLists() {
        reservationGrid.setItems(reservationService.getReservationListWithFilters(customer.getValue(), date.getValue(), receptionVenue.getValue(), reservationStatus.getValue()));
        rentalGrid.setItems(rentalService.getRentalListWithFilters(customer.getValue(), date.getValue(), receptionVenue.getValue(), reservationStatus.getValue()));
        returnGrid.setItems(returnService.getReturnListWithFilters(customer.getValue(), date.getValue(), receptionVenue.getValue(), reservationStatus.getValue()));
    }

    private void configureReservationGrid() {
        reservationGrid.setSizeFull();
        reservationGrid.setAllRowsVisible(true);
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
        rentalGrid.setAllRowsVisible(true);
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
        returnGrid.setAllRowsVisible(true);
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
