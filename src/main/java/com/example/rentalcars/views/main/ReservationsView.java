package com.example.rentalcars.views.main;

import com.example.rentalcars.DTO.ReservationDto;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "reservations", layout = MainLayout.class)
@PageTitle("Rezerwacje")
public class ReservationsView extends VerticalLayout {

    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);
    TextField filterText = new TextField();
    RentalForm form;

    public ReservationsView(ReservationService reservationService, EmployeeService employeeService) {
        this.reservationService = reservationService;
        this.employeeService = employeeService;
        addClassName("reservations-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(grid, getContent());
        updateReservationList();
    }

    private Component getContent(){
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(2, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateReservationList() {
        grid.setItems(reservationService.getReservationList());
    }

    private void configureForm() {
        form = new RentalForm(employeeService.getEmployeeList());
        form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue", "returnVenue", "customer.firstName", "customer.lastName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }



}
