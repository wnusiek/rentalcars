package com.example.rentalcars.views.main;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.DTO.ReservationDto;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route(value = "reservations", layout = MainLayout.class)
@PageTitle("Rezerwacje")
public class ReservationsView extends VerticalLayout {

    private final ReservationService reservationService;
    Grid<ReservationDto> grid = new Grid<>(ReservationDto.class);
    TextField filterText = new TextField();
    RentalForm form;

    public ReservationsView(ReservationService reservationService) {
        this.reservationService = reservationService;
        addClassName("reservations-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(grid, getContent());
//        add(getToolbar(), grid);

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
        form = new RentalForm();
        form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue", "returnVenue", "customer.firstName", "customer.lastName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

//    private HorizontalLayout getToolbar() {
//        return null;
//    }


}
