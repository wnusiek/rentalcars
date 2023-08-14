package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "customerView", layout = MainLayout.class)
@PageTitle("Moje konto")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class CustomerView extends VerticalLayout {

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final UserService userService;
    Grid<ReservationModel> grid = new Grid<>(ReservationModel.class);
    CustomerForm form = new CustomerForm();

    public CustomerView(ReservationService reservationService, CustomerService customerService, UserService userService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.userService = userService;
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

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateReservationList() {
        grid.setItems(reservationService.getReservationListLoggedUser());
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

    private void saveCustomer(CustomerForm.SaveEvent event) {
        customerService.saveCustomer(event.getCustomer());
        CustomerModel customer = event.getCustomer();
        customer.setUser(userService.findUserByNameModel(userService.getNameOfLoggedUser()));
        userService.syncEmail(customer);
        customerService.updateCustomer(customer);
        closeEditor();
    }

//    private Component getToolbar() {
//        Button addCustomerButton = new Button("Moje dane");
//
//        if (customerService.checkIfCustomerExist()) {
//
//            addCustomerButton.addClickListener(e -> addCustomer());
//
//        } else {
//
//            addCustomerButton.addClickListener(e -> editCustomer(customerService.findCustomerByName(userService.getNameOfLoggedUser())));
//
//        }
//
//
//        HorizontalLayout toolbar = new HorizontalLayout(addCustomerButton);
//        toolbar.addClassName("toolbar");
//        return toolbar;
//    }

    private Component getToolbar() {

        Button addCustomerButton = new Button("Wprowadź swoje dane");

        Button editCustomerButton = new Button("Edytuj swoje dane");

            editCustomerButton.addClickListener(e -> editCustomer(customerService.getCustomerByUserName(userService.getNameOfLoggedUser())));

            addCustomerButton.addClickListener(e -> addCustomer());


        HorizontalLayout toolbar = new HorizontalLayout(addCustomerButton, editCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCustomer() {
        if (customerService.getCustomerByUserName(userService.getNameOfLoggedUser()) != null)  {
            closeEditor();
        } else editCustomer(new CustomerModel());
    }

    private void editCustomer(CustomerModel customerModel) {
        if (customerModel == null) {
            closeEditor();
        } else {
            form.setCustomer(customerModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }


}
