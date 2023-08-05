package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "customerReservations", layout = MainLayout.class)
@PageTitle("Moje rezerwacje")
@Secured("ROLE_USER")
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class CustomerView extends VerticalLayout {

    private final ReservationService reservationService;
    private final CustomerService customerService;

    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);

    CustomerForm form = new CustomerForm();

    public CustomerView(ReservationService reservationService, CustomerService customerService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateReservationList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setSizeFull();
        return content;
    }

    private void updateReservationList() {
        grid.setItems(reservationService.getReservationList());
    }

    private void configureGrid() {
        grid.addClassNames("reservations-grid");
        grid.setSizeFull();
        grid.setColumns("car.mark", "car.model", "dateFrom", "dateTo", "price", "receptionVenue.city", "returnVenue.city");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureForm() {
        form = new CustomerForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveCustomer);
        form.addDeleteListener(this::deleteCustomer);
        form.addCloseListener(event -> closeEditor());
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        customerService.deleteCustomer(event.getCustomer());
        closeEditor();
    }

    private void saveCustomer(CustomerForm.SaveEvent event){
        customerService.saveCustomer(event.getCustomer());
        closeEditor();
    }

    private Component getToolbar(){
        Button addCustomerButton = new Button("Moje dane");
        addCustomerButton.addClickListener(e->addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(addCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCustomer() {
        editCustomer(new CustomerModel());
    }

    private void editCustomer(CustomerModel customerModel) {
        if(customerModel == null){
            closeEditor();
        }else {
            form.setCustomer(customerModel);
            addClassName("editing");
       }
    }

    private void closeEditor() {
        form.setCustomer(null);
        removeClassName("editing");
    }


}
