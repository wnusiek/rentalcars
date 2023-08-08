package com.example.rentalcars.views.main;

import com.example.rentalcars.security.SecurityService;
import com.example.rentalcars.security.SecurityUtils;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.customer.AddReservationView;
import com.example.rentalcars.views.main.customer.CustomerView;
import com.example.rentalcars.views.main.employee.*;
import com.example.rentalcars.views.main.manager.CompanyView;
import com.example.rentalcars.views.main.manager.DepartmentsEmployeesView;
import com.example.rentalcars.views.main.manager.DepartmentsView;
import com.example.rentalcars.views.main.manager.EmployeesView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@PermitAll
public class MainLayout extends AppLayout {

    private final CompanyService companyService;
    private SecurityService securityService;
    private final UserService userService;
    private String loggedUserName;
    private List<Class<? extends Component>> allowedViews;

    public MainLayout(CompanyService companyService, SecurityService securityService, UserService userService) {
        this.companyService = companyService;
        this.securityService = securityService;
        this.userService = userService;
        loggedUserName = userService.getNameOfLoggedUser();
        createHeader();
        createDrawer();

        allowedViews = new ArrayList<>();

        //Widoki dostępne dla wszystkich
        allowedViews.add(AddReservationView.class);
//        allowedViews.add();
//        allowedViews.add();


        //Widoki dla admina
        if (SecurityUtils.isUserAdmin()){
            allowedViews.add(AddRentalView.class);
            allowedViews.add(AddReturnView.class);
            allowedViews.add(CarsView.class);
            allowedViews.add(CompanyView.class);
            allowedViews.add(CustomerListView.class);
            allowedViews.add(DepartmentCarsView.class);
            allowedViews.add(DepartmentsEmployeesView.class);
            allowedViews.add(DepartmentsView.class);
            allowedViews.add(EmployeesView.class);
            allowedViews.add(RentalView.class);
            allowedViews.add(ReservationsView.class);
            allowedViews.add(ReturnListView.class);

        }
        if (SecurityUtils.isUserRegular()){
            allowedViews.add(CustomerView.class);
        }

    }

    public void navigateTo(Class<? extends Component> viewClass){
        getUI().ifPresent(ui->ui.navigate(viewClass));
    }


    private void createHeader() {
        H1 companyName = new H1("Wypożyczalnia samochodów " + companyService.getCompanyName());
        companyName.addClassNames("text-l", "m-m");

        Button registerButton = new Button("Zarejestruj się");
        registerButton.addClickListener(e -> UI.getCurrent().navigate("register"));

        Button logoutButton = new Button("Log out " + loggedUserName, e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), companyName, registerButton, logoutButton);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(companyName);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
    private void createDrawer() {
        RouterLink carsView = new RouterLink("Lista samochodów", CarsView.class);
        carsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink addRentalView = new RouterLink("Wypożyczanie", AddRentalView.class);
        addRentalView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink companyView = new RouterLink("Konfiguracja", CompanyView.class);
        companyView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink employeesView = new RouterLink("Lista pracowników", EmployeesView.class);
        employeesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customersListView = new RouterLink("Lista klientów", CustomerListView.class);
        customersListView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reservationsListView = new RouterLink("Lista rezerwacji", ReservationsView.class);
        reservationsListView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink addReservationView = new RouterLink("Rezerwowanie", AddReservationView.class);
        addReservationView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentsView = new RouterLink("Oddziały", DepartmentsView.class);
        departmentsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink rentalsView = new RouterLink("Lista wypożyczeń", RentalView.class);
        rentalsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink addReturnView = new RouterLink("Zwroty", AddReturnView.class);
        addReturnView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink returnsView = new RouterLink("Lista zwrotów", ReturnListView.class);
        returnsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentsemployeesView = new RouterLink("Lista pracowników oddziałów", DepartmentsEmployeesView.class);
        departmentsemployeesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentsCarsView = new RouterLink("Lista samochodów oddziałów", DepartmentCarsView.class);
        departmentsCarsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customerView = new RouterLink("Widok klienta", CustomerView.class);
        customerView.setHighlightCondition(HighlightConditions.sameLocation());

        if (SecurityUtils.isUserAdmin()){
            addToDrawer(new VerticalLayout(

                    addReservationView,
                    companyView,
                    carsView,
                    reservationsListView,
                    addRentalView,
                    rentalsView,
                    addReturnView,
                    returnsView,
                    employeesView,
                    customersListView,
                    departmentsView,
                    departmentsemployeesView,
                    departmentsCarsView,
                    customerView
            ));
        }

        if (SecurityUtils.isUserRegular()){
            addToDrawer(new VerticalLayout(
                    customerView,
                    addReservationView
            ));
        }



    }


}
