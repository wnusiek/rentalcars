package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.RentalService;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.service.ReturnService;
import com.example.rentalcars.views.main.MainLayout;
import com.example.rentalcars.views.main.employee.ReturnForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "return", layout = MainLayout.class)
@PageTitle("Zwroty")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")

public class ReturnView extends VerticalLayout {
    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    private final ReturnService returnService;
    private final RentalService rentalService;
    Grid<RentalModel> grid = new Grid<>(RentalModel.class, false);
    ReturnForm form;

    public ReturnView(ReservationService reservationService, EmployeeService employeeService, ReturnService returnService, RentalService rentalService) {
        this.reservationService = reservationService;
        this.employeeService = employeeService;
        this.returnService = returnService;
        this.rentalService = rentalService;
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
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(2, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateRentalList(){
        grid.setItems(rentalService.getRentalList());
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
    }

    private HorizontalLayout getToolbar(){
        Button addReturnButton = new Button("Dodaj zwrot");
        addReturnButton.addClickListener(e -> addReturn());

        var toolbar = new HorizontalLayout(addReturnButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReturn(){
        grid.asSingleSelect().clear();
        editReturn(new ReturnModel());
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
    }

    private void editReturn(ReturnModel returnModel){
        if (returnModel == null){
            closeEditor();
        }else {
            form.setReturn(returnModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }


}

