package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.CustomerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Lista klientów")
public class CustomerListView extends VerticalLayout {

    private final CustomerService customerService;

    Grid<CustomerModel> grid = new Grid<>(CustomerModel.class);

    public CustomerListView(CustomerService customerService) {
        this.customerService = customerService;
        addClassName("employees-view");
        setSizeFull();
        configureGrid();
        add(
                grid,
                getContent());
        updateCustomerList();
    }

    private void updateCustomerList() {
        grid.setItems(customerService.getCustomerList());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("employees-grid");
        grid.setSizeFull();
        grid.setColumns(
                "firstName",
                "lastName",
                "phoneNumber",
                "driverLicenseNumber",
                "email",
                "pesel",
                "city",
                "zipCode"
        );
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

//        grid.asSingleSelect().addValueChangeListener(event -> editEmployee(event.getValue()));
    }
}
