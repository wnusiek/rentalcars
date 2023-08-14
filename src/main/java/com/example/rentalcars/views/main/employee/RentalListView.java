package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.RentalService;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.views.main.MainLayout;
import com.example.rentalcars.views.main.employee.RentalForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.Collections;

@Route(value = "rentallist", layout = MainLayout.class)
@PageTitle("Lista wypożyczeń")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class RentalListView extends VerticalLayout {

    private final RentalService rentalService;
    private final EmployeeService employeeService;
    private final ReservationService reservationService;
    Grid<RentalModel> rentalGrid = new Grid<>(RentalModel.class);
    RentalForm rentalForm = new RentalForm(Collections.emptyList(), Collections.emptyList());

    public RentalListView(RentalService rentalService, EmployeeService employeeService, ReservationService reservationService) {
        this.rentalService = rentalService;
        this.employeeService = employeeService;
        this.reservationService = reservationService;
        addClassName("rentals-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
//                getToolbar(),
                getContent()
        );
        updateRentalList();
        closeEditor();
    }

    private void closeEditor() {
        rentalForm.setRental(null);
        rentalForm.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(rentalGrid, rentalForm);
        content.setFlexGrow(2, rentalGrid);
        content.setFlexGrow(1, rentalForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateRentalList(){
        rentalGrid.setItems(rentalService.getRentalList());
    }

    private void configureGrid(){
        rentalGrid.addClassNames("rentals-grid");
        rentalGrid.setSizeFull();
        rentalGrid.setColumns("employee.firstName", "employee.lastName", "dateOfRental", "reservation.customer.firstName", "reservation.customer.lastName", "comments");
        rentalGrid.getColumns().forEach(col -> col.setAutoWidth(true));

//        rentalGrid.asSingleSelect().addValueChangeListener(event -> editRental(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        Button addRentalButton = new Button("Add rental");
        addRentalButton.addClickListener(e->addRental());

        var toolbar = new HorizontalLayout(addRentalButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRental() {
        rentalGrid.asSingleSelect().clear();
        editRental(new RentalModel());
    }

    private void configureForm(){
        rentalForm = new RentalForm(employeeService.getEmployeeList(), reservationService.getReservationList());
        rentalForm.setWidth("25em");

        rentalForm.addSaveListener(this::saveRental);
//        rentalForm.addDeleteListener(this::deleteRental);
        rentalForm.addCloseListener(event -> closeEditor());


    }

//    private void deleteRental(RentalForm.DeleteEvent event){
//        rentalService.deleteRental(event.getRental());
//        updateRentalList();
//        closeEditor();
//    }

    private void saveRental(RentalForm.SaveEvent event){
        rentalService.postAddRental(event.getRental());
    }

    private void editRental(RentalModel rentalModel) {
        if (rentalModel == null){
            closeEditor();
        }else {
            rentalForm.setRental(rentalModel);
            rentalForm.setVisible(true);
            addClassName("editing");
        }
    }

}
