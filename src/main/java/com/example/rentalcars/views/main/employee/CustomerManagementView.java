package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.views.main.MainLayout;
import com.example.rentalcars.views.main.customer.CustomerForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "customermanagement", layout = MainLayout.class)
@PageTitle("Klienci")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class CustomerManagementView extends VerticalLayout {

    private final CustomerService customerService;
    Grid<CustomerModel> grid = new Grid<>(CustomerModel.class);
    CustomerForm form = new CustomerForm();

    public CustomerManagementView(CustomerService customerService) {
        this.customerService = customerService;
        addClassName("employees-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent());
        updateCustomerList();
        closeEditor();

    }
    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    private void updateCustomerList() {
        grid.setItems(customerService.getCustomerList());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
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
        updateCustomerList();
        closeEditor();
    }

    private void saveCustomer(CustomerForm.SaveEvent event){
        customerService.postAddCustomer(event.getCustomer());
        updateCustomerList();
        closeEditor();
    }

    private Component getToolbar(){
        Button addCustomerButton = new Button("Add customer");
        addCustomerButton.addClickListener(e->addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(addCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCustomer() {
        grid.asSingleSelect().clear();
        editCustomer(new CustomerModel());
    }

    private void editCustomer(CustomerModel customerModel) {
        if(customerModel == null){
            closeEditor();
        }else {
            form.setCustomer(customerModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureGrid() {
        grid.addClassNames("employees-grid");
        grid.setSizeFull();
        grid.setColumns(
                "firstName",
                "lastName",
                "phoneNumber",
                "driverLicenceNumber",
                "email",
                "pesel",
                "city",
                "zipCode"
        );
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editCustomer(event.getValue()));
    }
}
