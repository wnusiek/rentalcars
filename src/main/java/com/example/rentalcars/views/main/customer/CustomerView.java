package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.NoSuchElementException;

@Route(value = "customerView", layout = MainLayout.class)
@PageTitle("Moje dane")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;
    private final UserService userService;
    CustomerForm form = new CustomerForm();

    public CustomerView(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
        setSizeFull();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(form);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
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
//        CustomerModel customer = event.getCustomer();
//        UserModel currentUser = userService.findUserByName(userService.getNameOfLoggedUser());
//        currentUser = userService.findById(currentUser.getId());
//        customer.setUser(currentUser);
//        customerService.updateCustomer(customer);
        closeEditor();
    }

    private Component getToolbar() {
        Button addCustomerButton = new Button("Wprowadź swoje dane");
        Button editCustomerButton = new Button("Edytuj swoje dane");
            CustomerModel customer = customerService.getCustomerByUserName(userService.getNameOfLoggedUser());
            if (customer == null){
                editCustomerButton.setVisible(false);
            } else {
                addCustomerButton.setVisible(false);
            }
            editCustomerButton.addClickListener(e -> editCustomer(customer));
            addCustomerButton.addClickListener(e -> addCustomer());
        HorizontalLayout toolbar = new HorizontalLayout(addCustomerButton, editCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCustomer() {

        UserModel currentUser = userService.findUserByName(userService.getNameOfLoggedUser());
        currentUser = userService.findById(currentUser.getId());

        CustomerModel customerModel = new CustomerModel();
        customerModel.setUser(currentUser);
        customerService.updateCustomer(customerModel);

        editCustomer(customerModel);
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
