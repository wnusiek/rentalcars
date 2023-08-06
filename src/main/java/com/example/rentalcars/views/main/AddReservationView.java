package com.example.rentalcars.views.main;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.vaadinService.RentalVaadinService;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Collections;
import java.util.InputMismatchException;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Rezerwowanie")
@PermitAll
public class AddReservationView extends VerticalLayout {
    private final RentalVaadinService service;
    private final ReservationService reservationService;
    private final DepartmentService departmentService;
    private final CarService carService;
    private final CustomerService customerService;
    Grid<CarModel> carGrid = new Grid<>(CarModel.class);
    TextField filterText = new TextField();
    DatePicker startDate = new DatePicker();
    DatePicker endDate = new DatePicker();
    ComboBox<DepartmentModel> departmentModelComboBox = new ComboBox<>();
    Checkbox carStatusCheckBox = new Checkbox("Tylko dostępne");



    ReservationForm reservationForm = new ReservationForm(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

    public AddReservationView(RentalVaadinService service, ReservationService reservationService, DepartmentService departmentService, CarService carService, CustomerService customerService) {
        this.service = service;
        this.reservationService = reservationService;
        this.departmentService = departmentService;
        this.carService = carService;
        this.customerService = customerService;
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
            carGrid.setItems(service.findAvailableCarsByDates(carStatusCheckBox.getValue(), departmentModelComboBox.getValue(), startDate.getValue(), endDate.getValue()));
    }

    private void configureGrid() {
        carGrid.addClassNames("cars-grid");
        carGrid.setSizeFull();
        carGrid.setColumns("mark", "model", "body", "color", "fuelType", "gearbox", "price", "availability");
        carGrid.getColumns().forEach(col -> col.setAutoWidth(true));

//        carGrid.asSingleSelect().addValueChangeListener(e ->editReservation(e.getValue()));
    }

    private HorizontalLayout getToolbar() {
        Button addReservationButton = new Button("Add reservation");
        addReservationButton.addClickListener(e -> addReservation());
        carStatusCheckBox.addValueChangeListener(e -> updateCarList());
        departmentModelComboBox.setPlaceholder("Oddziały");
        departmentModelComboBox.setItems(departmentService.getDepartmentList1());
        departmentModelComboBox.setItemLabelGenerator(DepartmentModel::getCity);
        startDate.setPlaceholder("Set start date");
        endDate.setPlaceholder("Set end date");
        startDate.setClearButtonVisible(true);
        endDate.setClearButtonVisible(true);
        departmentModelComboBox.setClearButtonVisible(true);
        startDate.addValueChangeListener(e -> updateCarList());
        endDate.addValueChangeListener(e -> updateCarList());
        departmentModelComboBox.addValueChangeListener(e -> updateCarList());

        var toolbar = new HorizontalLayout(departmentModelComboBox, startDate, endDate, carStatusCheckBox, addReservationButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReservation() {
        carGrid.asSingleSelect().clear();
        editReservation(new ReservationModel());
    }

    private void configureForm() {
        reservationForm = new ReservationForm(departmentService.getDepartmentList1(), carService.getCarList1(), customerService.getCustomerList());
        reservationForm.setWidth("25em");

        reservationForm.addSaveListener(this::saveReservation);
//        reservationForm.addDeleteListener(this::deleteReservation);
        reservationForm.addCloseListener(event -> closeEditor());
    }

    private void saveReservation(ReservationForm.SaveEvent event) {
        try{
            reservationService.addReservation(event.getReservation());
            closeEditor();
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
