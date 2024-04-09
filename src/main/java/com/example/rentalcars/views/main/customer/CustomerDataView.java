package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CustomerModel;
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

@Route(value = "customerDataView", layout = MainLayout.class)
@PageTitle("Moje dane")
@Secured("ROLE_CUSTOMER")
@RolesAllowed("ROLE_CUSTOMER")
public class CustomerDataView extends VerticalLayout {

    private final CustomerService customerService;
    private final UserService userService;
    CustomerDataForm form = new CustomerDataForm();

    public CustomerDataView(CustomerService customerService, UserService userService) {
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
        form = new CustomerDataForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveCustomer);
        form.addCloseListener(event -> closeEditor());
    }

    private void saveCustomer(CustomerDataForm.SaveEvent event) {
        customerService.saveCustomer(event.getCustomer());
        closeEditor();
    }

    private Component getToolbar() {

        Button editCustomerButton = new Button("Edytuj swoje dane");
        CustomerModel customer = customerService.getCustomerByUserName(userService.getNameOfLoggedUser());
        editCustomerButton.addClickListener(e -> editCustomer(customer));

        HorizontalLayout toolbar = new HorizontalLayout(editCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
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
