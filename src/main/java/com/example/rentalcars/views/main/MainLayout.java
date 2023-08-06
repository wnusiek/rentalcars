package com.example.rentalcars.views.main;

import com.example.rentalcars.repository.CompanyRepository;
import com.example.rentalcars.security.SecurityService;
import com.example.rentalcars.service.UserService;
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

@PermitAll
public class MainLayout extends AppLayout {

    private final CompanyRepository companyRepository;
    private SecurityService securityService;
    private final UserService userService;
    private String loggedUserName;
    public MainLayout(CompanyRepository companyRepository, SecurityService securityService, UserService userService) {
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.userService = userService;
        loggedUserName = userService.getNameOfLoggedUser();
        createHeader();
        createDrawer();
    }
    private void createHeader() {
        H1 companyName = new H1(companyRepository.findById(1l).get().getCompanyName());
        companyName.addClassNames("text-l", "m-m");


        Button logOut = new Button("Log out " + loggedUserName, e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), companyName, logOut);

        //todo
        Button register = new Button("Register");

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

        addToDrawer(new VerticalLayout(
                companyView,
                carsView,
                addReservationView,
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


}
