package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.*;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "returnView", layout = MainLayout.class)
@PageTitle("Zwroty")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})

public class ReturnView extends VerticalLayout {
    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    private final ReturnService returnService;
    private final RentalService rentalService;
    private final UserService userService;
    Grid<RentalModel> grid = new Grid<>(RentalModel.class, false);
    DatePicker dateOfReturn = new DatePicker("Data zwrotu");
    Button returnACarButton = new Button("Zwróć");
    TextField comments = new TextField("Komentarz");
    ReturnForm form;
    RentalModel employeeChoice;

    public ReturnView(ReservationService reservationService, EmployeeService employeeService, ReturnService returnService, RentalService rentalService, UserService userService) {
        this.reservationService = reservationService;
        this.employeeService = employeeService;
        this.returnService = returnService;
        this.rentalService = rentalService;
        this.userService = userService;
        addClassName("return-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateRentalList();
        closeEditor();
    }

    private void closeEditor() {
        form.setReturn(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent(){
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateRentalList(){
        grid.setItems(rentalService.getRentalListOfNotReturnedCars());
    }

    private void configureGrid() {
        grid.addClassNames("rental-grid");
        grid.setSizeFull();

        grid.addColumn("employee.firstName").setHeader("Imię pracownika");
        grid.addColumn("employee.lastName").setHeader("Nazwisko pracownika");
        grid.addColumn("reservation.car.mark").setHeader("Marka");
        grid.addColumn("reservation.car.model").setHeader("Model");
        grid.addColumn("reservation.dateFrom").setHeader("Data rezerwacji od");
        grid.addColumn("reservation.dateTo" ).setHeader("Data rezerwacji  do");
        grid.addColumn("reservation.customer.firstName").setHeader("Imię klienta");
        grid.addColumn("reservation.customer.lastName").setHeader("Nazwisko klienta");
        grid.addColumn("reservation.price").setHeader("Cena");
        grid.addColumn("dateOfRental").setHeader("Data wypożyczenia");
        grid.addColumn("comments").setHeader("Komentarz");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> saveEmployeeChoice(event.getValue()));
    }

    private void saveEmployeeChoice(RentalModel employeeChoice) {
        this.employeeChoice = employeeChoice;
    }

    private HorizontalLayout getToolbar(){
        dateOfReturn.setPlaceholder("Wybierz datę");
        dateOfReturn.setClearButtonVisible(true);
        returnACarButton.addClickListener(event -> validateFields());
        comments.setPlaceholder("Dodaj komentarz");
        var toolbar = new HorizontalLayout(dateOfReturn, comments, returnACarButton);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
    }

    private void validateFields() {
        if (dateOfReturn.isEmpty()){
            Notification.show("Wybierz datę zwrotu").setPosition(Notification.Position.MIDDLE);
        } else if (grid.asSingleSelect().isEmpty()) {
            Notification.show("Wybierz wypożyczenie").setPosition(Notification.Position.MIDDLE);
        } else {
            addReturn();
        }
    }

    private void addReturn(){
        ReturnModel returnModel = new ReturnModel();
        returnModel.setEmployee(employeeService.getEmployeeByUserName(userService.getNameOfLoggedUser()));
        returnModel.setReservation(employeeChoice.getReservation());
        returnModel.setDateOfReturn(dateOfReturn.getValue());
        returnModel.setComments(comments.getValue());
        editReturn(returnModel);
    }

    private void configureForm() {
        form = new ReturnForm(employeeService.getEmployeeList(), reservationService.getReservationList());
        form.setWidth("25em");

        form.addSaveListener(this::saveReturn);
        form.addCloseListener(cancelEvent -> closeEditor());
    }

    private void saveReturn(ReturnForm.SaveEvent event){
        returnService.addReturn(event.getReturn());
        closeEditor();
        updateRentalList();
        comments.clear();
    }

    private void editReturn(ReturnModel returnModel){
        if (returnModel == null){
            closeEditor();
        }else {
            form.setReturn(returnModel);
            form.save.click();
            addClassName("editing");
        }
    }


}

