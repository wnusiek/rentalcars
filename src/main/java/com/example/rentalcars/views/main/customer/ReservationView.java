package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.*;
import com.example.rentalcars.vaadinService.RentalVaadinService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Collections;
import java.util.InputMismatchException;

@Route(value = "reservation", layout = MainLayout.class)
@PageTitle("Rezerwowanie")
@AnonymousAllowed
public class ReservationView extends VerticalLayout {
    private final RentalVaadinService service;
    private final ReservationService reservationService;
    private final DepartmentService departmentService;
    private final CarService carService;
    private final CustomerService customerService;
    private final UserService userService;
    Grid<CarModel> carGrid = new Grid<>(CarModel.class);
    TextField filterText = new TextField();
    DatePicker startDate = new DatePicker("Data odbioru");
    DatePicker endDate = new DatePicker("Data zwrotu");
    ComboBox<DepartmentModel> receptionVenueComboBox = new ComboBox<>("Oddział odbioru");
    ComboBox<DepartmentModel> returnVenueCombobox = new ComboBox<>("Oddział zwrotu");
    Checkbox carStatusCheckBox = new Checkbox("Tylko dostępne");
    Button makeReservationButton = new Button("Zarezerwuj");


    ReservationForm reservationForm = new ReservationForm(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

    public ReservationView(RentalVaadinService service, ReservationService reservationService, DepartmentService departmentService, CarService carService, CustomerService customerService, UserService userService) {
        this.service = service;
        this.reservationService = reservationService;
        this.departmentService = departmentService;
        this.carService = carService;
        this.customerService = customerService;
        this.userService = userService;
        addClassName("list-view");

        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateCarList();
        closeEditor();
    }

    private void closeEditor() {
        reservationForm.setReservation(null);
        reservationForm.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(carGrid, reservationForm);
        content.setFlexGrow(2, carGrid);
        content.setFlexGrow(1, reservationForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateCarList() {
            carGrid.setItems(service.findAvailableCarsByDates(carStatusCheckBox.getValue(), receptionVenueComboBox.getValue(), startDate.getValue(), endDate.getValue()));
    }

    private void configureGrid() {
        carGrid.addClassNames("cars-grid");
        carGrid.setSizeFull();
        carGrid.setColumns("mark", "model", "body", "color", "fuelType", "gearbox", "price", "availability");
        carGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        carGrid.asSingleSelect().addValueChangeListener(e ->addReservation(e.getValue()));
    }

    private HorizontalLayout getToolbar() {
        carStatusCheckBox.addValueChangeListener(e -> updateCarList());
        receptionVenueComboBox.setPlaceholder("wybierz oddział");
        receptionVenueComboBox.setItems(departmentService.getDepartmentList1());
        receptionVenueComboBox.setItemLabelGenerator(DepartmentModel::getCity);
        startDate.setPlaceholder("Wybierz datę");
        endDate.setPlaceholder("Wybierz datę");
        startDate.setClearButtonVisible(true);
        endDate.setClearButtonVisible(true);
        receptionVenueComboBox.setClearButtonVisible(true);
        startDate.addValueChangeListener(e -> updateCarList());
        endDate.addValueChangeListener(e -> updateCarList());
        receptionVenueComboBox.addValueChangeListener(e -> updateCarList());

        returnVenueCombobox.setPlaceholder("Wybierz oddział");
        returnVenueCombobox.setItems(departmentService.getDepartmentList1());
        returnVenueCombobox.setItemLabelGenerator(DepartmentModel::getCity);
        returnVenueCombobox.setClearButtonVisible(true);

        makeReservationButton.setVisible(false);

        var toolbar = new HorizontalLayout(receptionVenueComboBox, returnVenueCombobox,startDate, endDate, carStatusCheckBox, makeReservationButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReservation(CarModel carModel) {
        if (!startDate.isEmpty() && !endDate.isEmpty() && !receptionVenueComboBox.isEmpty() && !returnVenueCombobox.isEmpty()){

        makeReservationButton.setVisible(true);
//        makeReservationButton.addClickListener(event -> )
        }
        else {
            Notification.show("Wypełnij wszystkie pola").setPosition(Notification.Position.MIDDLE);
        }

//        carGrid.asSingleSelect().clear();
        CustomerModel customerModel;
        customerModel = customerService.getCustomerByUserName(userService.getNameOfLoggedUser());
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setCustomer(customerModel);
        reservationModel.setCar(carModel);
        reservationModel.setDateFrom(startDate.getValue());
        reservationModel.setDateTo(endDate.getValue());
        reservationModel.setReceptionVenue(receptionVenueComboBox.getValue());
        reservationModel.setReturnVenue(returnVenueCombobox.getValue());
        editReservation(reservationModel);
    }

    private void configureForm() {
        reservationForm = new ReservationForm(departmentService.getDepartmentList1(), carService.getCarList1(), customerService.getCustomerList());
        reservationForm.setWidth("25em");

        reservationForm.addSaveListener(this::saveReservation);
        reservationForm.addCloseListener(event -> closeEditor());
    }

    private void saveReservation(ReservationForm.SaveEvent event) {
        try{
            reservationService.addReservation(event.getReservation());
            closeEditor();
//            makeReservationButton.setVisible(false);
        } catch (IllegalArgumentException | InputMismatchException e){
            Notification notification = Notification
                    .show(e.getMessage(), 3000, Notification.Position.MIDDLE);
        }

    }

    private void editReservation(ReservationModel reservationModel) {
        if (reservationModel == null) {
            closeEditor();
        } else {
            reservationForm.setReservation(reservationModel);
            reservationForm.setVisible(true);
            addClassName("editing");

        }
    }



}
