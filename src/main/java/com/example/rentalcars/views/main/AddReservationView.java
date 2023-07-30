package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "addreservation", layout =  MainLayout.class)
@PageTitle("Rezerwowanie")
public class AddReservationView extends VerticalLayout {
    private final ReservationService reservationService;
    private final DepartmentService departmentService;
    private final CarService carService;
    private final CustomerService customerService;
    Grid<CarModel> carGrid = new Grid<>(CarModel.class);
    TextField filterText = new TextField();
    ReservationForm reservationForm = new ReservationForm();

    public AddReservationView(ReservationService reservationService, DepartmentService departmentService, CarService carService, CustomerService customerService) {
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
        carGrid.setItems(carService.findCarsByMark(filterText.getValue()));
    }

    private void configureGrid() {
        carGrid.addClassNames("cars-grid");
        carGrid.setSizeFull();
        carGrid.setColumns("mark", "model", "body", "color", "fuelType", "gearbox", "price", "availability");
        carGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        carGrid.asSingleSelect().addValueChangeListener(e ->editReservation(e.getValue()));
    }

    private HorizontalLayout getToolbar() {
        Button addReservationButton = new Button("Add reservation");
        addReservationButton.addClickListener(e->addReservation(e.));

        filterText.setPlaceholder("Filter by mark...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCarList());

        var toolbar = new HorizontalLayout(filterText, addReservationButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReservation(){
        carGrid.asSingleSelect().clear();
        editReservation(new ReservationModel());
    }
    private void configureForm(){
        reservationForm = new ReservationForm(departmentService.getDepartmentList1(), carService.getCarList1(), customerService.getCustomerList());
        reservationForm.setWidth("25em");

        reservationForm.addSaveListener(this::saveReservation);
//        reservationForm.addDeleteListener(this::deleteReservation);
        reservationForm.addCloseListener(event -> closeEditor());
    }
    private void saveReservation(ReservationForm.SaveEvent event){
        reservationService.addReservation(event.getReservation());
        closeEditor();
    }

    private void editReservation(ReservationModel reservationModel){
        if (reservationModel == null){
            closeEditor();
        }else {
            reservationForm.setReservation(reservationModel);
            reservationForm.setVisible(true);
            addClassName("editing");

        }
    }
}
