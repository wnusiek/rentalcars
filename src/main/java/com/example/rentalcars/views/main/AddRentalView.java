package com.example.rentalcars.views.main;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "addrental", layout = MainLayout.class)
@PageTitle("Wypożyczanie")
public class AddRentalView extends VerticalLayout {

    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);
    TextField filterText = new TextField();
    RentalForm form;

    public AddRentalView(ReservationService reservationService, EmployeeService employeeService) {
        this.reservationService = reservationService;
        this.employeeService = employeeService;
        addClassName("rental-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
//                getContent()
                grid,
                form
        );
        updateReservationList();
    }

//    private Component getContent(){
//        HorizontalLayout content = new HorizontalLayout(grid, form);
////        content.setFlexGrow(2, grid);
////        content.setFlexGrow(2, form);
////        content.addClassName("content");
//        content.setSizeFull();
//        return content;
//    }

    private void updateReservationList() {
        grid.setItems(reservationService.getReservationList());
    }

    private void configureForm() {
        form = new RentalForm(employeeService.getEmployeeList(), reservationService.getReservationList());
        form.setWidth("25em");
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue", "returnVenue", "customer.firstName", "customer.lastName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

//        grid.asSingleSelect().addValueChangeListener(event -> editRent(event.getValue()));
    }

//    private void editRent(ReservationModel reservationModel) {
//        form.setRental(reservationModel);
//    }


}
