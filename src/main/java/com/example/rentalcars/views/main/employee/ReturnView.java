package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.*;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@Route(value = "returnView", layout = MainLayout.class)
@PageTitle("Zwroty")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})

public class ReturnView extends VerticalLayout {
    private final ReservationService reservationService;
    private final DepartmentService departmentService;
    private final CarService carService;
    private final EmployeeService employeeService;
    private final ReturnService returnService;
    private final RentalService rentalService;
    private final UserService userService;
    Grid<RentalModel> grid = new Grid<>(RentalModel.class, false);
    DatePicker dateOfReturn = new DatePicker("Data zwrotu");
    Button returnACarButton = new Button("Zwróć");
    TextField comments = new TextField("Komentarz");
    BigDecimalField supplement = new BigDecimalField("Dopłata");
    ComboBox<DepartmentModel> returnVenue = new ComboBox<>("Oddział zwrotu");
    TextField mileage = new TextField("Przebieg");
    ReturnForm form;
    RentalModel employeeChoice;

    public ReturnView(ReservationService reservationService, DepartmentService departmentService, CarService carService, EmployeeService employeeService, ReturnService returnService, RentalService rentalService, UserService userService) {
        this.reservationService = reservationService;
        this.departmentService = departmentService;
        this.carService = carService;
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
        grid.setItems(rentalService.getRentalListOfNotReturnedCarsByReturnDepartment(returnVenue.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("rental-grid");
        grid.setSizeFull();
        grid.addColumn("reservation.car.mark").setHeader("Marka");
        grid.addColumn("reservation.car.model").setHeader("Model");
        grid.addColumn("dateOfRental").setHeader("Wypożyczenie od");
        grid.addColumn("reservation.dateFrom").setHeader("Rezerwacja od");
        grid.addColumn("reservation.dateTo" ).setHeader("Rezerwacja do");
        grid.addColumn("employee.firstName").setHeader("Imię pracownika");
        grid.addColumn("employee.lastName").setHeader("Nazwisko pracownika");
        grid.addColumn("reservation.customer.firstName").setHeader("Imię klienta");
        grid.addColumn("reservation.customer.lastName").setHeader("Nazwisko klienta");
        grid.addColumn("reservation.price").setHeader("Cena");
        grid.addColumn("comments").setHeader("Komentarz");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> saveEmployeeChoice(event.getValue()));
    }

    private void saveEmployeeChoice(RentalModel employeeChoice) {
        this.employeeChoice = employeeChoice;
        if (employeeChoice == null) {
            mileage.setValue("");
        } else {
            mileage.setValue(employeeChoice.getReservation().getCar().getMileage().toString());
        }
    }

    private HorizontalLayout getToolbar(){
        dateOfReturn.setPlaceholder("Wybierz datę");
        dateOfReturn.setClearButtonVisible(true);
        LocalDate now = LocalDate.now();
        dateOfReturn.setMin(now);
        dateOfReturn.setMax(now);
        dateOfReturn.setValue(LocalDate.now());
        returnACarButton.addClickListener(event -> validateFields());
        comments.setPlaceholder("Dodaj komentarz");
        returnVenue.setPlaceholder("Wybierz oddział");
        returnVenue.setClearButtonVisible(true);
        returnVenue.setItems(departmentService.getDepartmentList());
        returnVenue.setItemLabelGenerator(DepartmentModel::getCity);
        Optional<DepartmentModel> loggedEmployeeDepartment =
                departmentService.getDepartmentByEmployee(
                        employeeService.getEmployeeByUserName(
                                userService.getNameOfLoggedUser()));
        loggedEmployeeDepartment.ifPresent(departmentModel -> returnVenue.setValue(departmentModel));
        returnVenue.addValueChangeListener(e -> updateRentalList());
        supplement.setPlaceholder("Wprowadź koszty");
        supplement.setWidth("200px");
        Div plnPrefix = new Div();
        plnPrefix.setText("PLN");
        supplement.setPrefixComponent(plnPrefix);
        mileage.setPlaceholder("Zaktualizuj przebieg");
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.add(dateOfReturn, mileage, supplement, comments, returnACarButton);
        toolbar.addAndExpand(new Span());
        toolbar.add(returnVenue);
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
        returnModel.setSupplement(supplement.getValue());
        editReturn(returnModel);
    }

    private void configureForm() {
        form = new ReturnForm(employeeService.getEmployeeList(), reservationService.getReservationList());
        form.setWidth("25em");
        form.addSaveListener(this::saveReturn);
        form.addCloseListener(cancelEvent -> closeEditor());
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

    private void saveReturn(ReturnForm.SaveEvent event){
        ReturnModel returnModel = event.getReturn();
        ReservationModel reservationModel = returnModel.getReservation();
        Long carId = reservationModel.getCar().getId();
        Long returnVenueId = reservationModel.getReturnVenue().getId();
        Long reservationId = reservationModel.getId();
        BigDecimal afterDeadlineFee = BigDecimal.valueOf(0);
        boolean carReturnedAfterDeadline = returnModel.getDateOfReturn().isAfter(reservationModel.getDateTo());
        if(carReturnedAfterDeadline){
            Duration difference = Duration.between(returnModel.getDateOfReturn(), reservationModel.getDateTo());
            afterDeadlineFee = BigDecimal.valueOf(100*difference.toDays());
            Notification.show("Zwrot po terminie! Dodatkowa opłata: " + afterDeadlineFee).setPosition(Notification.Position.MIDDLE);
        }
        if (supplement.getValue() == null){
            returnModel.setSupplement(BigDecimal.valueOf(0));
        } else {
            // Brakuje sprawdzenia czy mniejsze od zera!!!
            returnModel.setSupplement(supplement.getValue());
            Notification.show("Dopłata: " + supplement.getValue()).setPosition(Notification.Position.MIDDLE);
        }
        returnModel.setTotalCost(reservationModel.getPrice()
                .add(returnModel.getSupplement())
                .add(afterDeadlineFee));
        returnService.addReturn(returnModel);
        departmentService.addCarToDepartment(carId, returnVenueId);
        carService.setCarStatus(carId, CarStatus.AVAILABLE);
        carService.setMileageByCarId(carId, Integer.parseInt(mileage.getValue()));
        reservationService.setReservationStatus(reservationId, ReservationStatus.RETURNED);
        closeEditor();
        updateRentalList();
        comments.clear();
        dateOfReturn.clear();
        supplement.clear();
        mileage.clear();
    }


}

