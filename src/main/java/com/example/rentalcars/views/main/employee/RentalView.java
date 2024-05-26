package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.*;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;

@Route(value = "rentalView", layout = MainLayout.class)
@PageTitle("Wypożyczanie")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})

public class RentalView extends VerticalLayout {
    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    private final RentalService rentalService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final CarService carService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class, false);
    DatePicker dateOfRental = new DatePicker("Data wypożyczenia");
    Button rentACarButton = new Button("Wypożycz");
    TextField comments = new TextField("Komentarz");
    ComboBox<DepartmentModel> receptionVenue = new ComboBox<>("Oddział odbioru");
    RentalForm form;
    ReservationModel employeeChoice;

    public RentalView(ReservationService reservationService, EmployeeService employeeService, RentalService rentalService, UserService userService, DepartmentService departmentService, CarService carService) {
        this.rentalService = rentalService;
        this.reservationService = reservationService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.carService = carService;
        addClassName("rental-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateReservationList();
        closeEditor();
    }

    private void closeEditor() {
        form.setRental(null);
        form.setVisible(false);
        removeClassName("editing");
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
        grid.setItems(reservationService.getReservationListOfNotRentedCarsByReceptionDepartment(receptionVenue.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.addColumn("car.mark").setHeader("Marka");
        grid.addColumn("car.model").setHeader("Model");
        grid.addColumn("dateFrom").setHeader("Od");
        grid.addColumn("dateTo").setHeader("Do");
        grid.addColumn("price").setHeader("Cena");
        grid.addColumn("receptionVenue.city").setHeader("Miejsce odbioru");
        grid.addColumn("returnVenue.city").setHeader("Miejsce zwrotu");
        grid.addColumn("customer.firstName").setHeader("Imię klienta");
        grid.addColumn("customer.lastName").setHeader("Nazwisko klienta");
        grid.addColumn("reservationStatus").setHeader("Status rezerwacji");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e-> saveEmployeeChoice(e.getValue()));
    }

    private void saveEmployeeChoice(ReservationModel employeeChoice) {
        this.employeeChoice = employeeChoice;
    }

    private HorizontalLayout getToolbar() {

        dateOfRental.setPlaceholder("Wybierz datę");
        dateOfRental.setClearButtonVisible(true);
        LocalDate now = LocalDate.now();
        dateOfRental.setMin(now);
        dateOfRental.setMax(now);
        rentACarButton.addClickListener(event -> validateFields());
        comments.setPlaceholder("Dodaj komentarz");
        receptionVenue.setPlaceholder("Wybierz oddział...");
        receptionVenue.setClearButtonVisible(true);
        receptionVenue.setItems(departmentService.getDepartmentList());
        receptionVenue.setItemLabelGenerator(DepartmentModel::getCity);
        receptionVenue.setValue(
                departmentService.getDepartmentByEmployee(
                        employeeService.getEmployeeByUserName(
                                userService.getNameOfLoggedUser())));
        receptionVenue.addValueChangeListener(e -> updateReservationList());
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.add(dateOfRental, comments, rentACarButton);
        toolbar.addAndExpand(new Span());
        toolbar.add(receptionVenue);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
    }

    private void validateFields() {
        if (dateOfRental.isEmpty()){
            Notification.show("Wybierz datę wypożyczenia").setPosition(Notification.Position.MIDDLE);
        } else if (grid.asSingleSelect().isEmpty()) {
            Notification.show("Wybierz rezerwację").setPosition(Notification.Position.MIDDLE);
        } else {
            addRental();
        }
    }

    private void addRental() {
        RentalModel rentalModel = new RentalModel();
        rentalModel.setEmployee(employeeService.getEmployeeByUserName(userService.getNameOfLoggedUser()));
        rentalModel.setReservation(employeeChoice);
        rentalModel.setDateOfRental(dateOfRental.getValue());
        rentalModel.setComments(comments.getValue());
        editRental(rentalModel);
    }

    private void configureForm() {
        form = new RentalForm(employeeService.getEmployeeList(), reservationService.getReservationListOfNotRentedCarsAllReceptionDepartments());
        form.setWidth("25em");
        form.addSaveListener(this::saveRental);
        form.addCloseListener(cancelEvent -> closeEditor());
    }

    private void saveRental(RentalForm.SaveEvent event){
        RentalModel rentalModel = event.getRental();
        rentalService.addRental(rentalModel);
        var reservation = rentalModel.getReservation();
        var carId = reservation.getCar().getId();
        var receptionId = reservation.getReceptionVenue().getId();
        departmentService.removeCarFromDepartment(carId, receptionId);
        carService.setCarStatus(carId, CarStatus.HIRED);
        var reservationId = reservation.getId();
        reservationService.setReservationStatus(reservationId, ReservationStatus.RENTED);
        closeEditor();
        updateReservationList();
    }

    private void editRental(RentalModel rentalModel){
        if (rentalModel == null){
            closeEditor();
        }else {
            form.setRental(rentalModel);
            form.save.click();
            addClassName("editing");
        }
    }


}
