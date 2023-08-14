package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
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

@Route(value = "addrental", layout = MainLayout.class)
@PageTitle("Wypożyczanie")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")

public class RentalView extends VerticalLayout {

    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    private final RentalService rentalService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);
    RentalForm form;

    public RentalView(ReservationService reservationService, EmployeeService employeeService, RentalService rentalService) {
        this.rentalService = rentalService;
        this.reservationService = reservationService;
        this.employeeService = employeeService;
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
        grid.setItems(reservationService.getReservationList());
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue.city", "returnVenue.city", "customer.firstName", "customer.lastName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private HorizontalLayout getToolbar() {
        Button addRentalButton = new Button("Add rental");
        addRentalButton.addClickListener(e->addRental());

        var toolbar = new HorizontalLayout(addRentalButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private void addRental() {
        grid.asSingleSelect().clear();
        editRental(new RentalModel());
    }

    private void configureForm() {
        form = new RentalForm(employeeService.getEmployeeList(), reservationService.getReservationList());
        form.setWidth("25em");

        form.addSaveListener(this::saveRental);
        form.addCloseListener(cancelEvent -> closeEditor());
    }

    private void saveRental(RentalForm.SaveEvent event){
        rentalService.postAddRental(event.getRental());
        closeEditor();
    }

    private void editRental(RentalModel rentalModel){
        if (rentalModel == null){
            closeEditor();
        }else {
            form.setRental(rentalModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }


}
