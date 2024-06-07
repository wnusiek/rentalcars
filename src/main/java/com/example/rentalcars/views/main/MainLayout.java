package com.example.rentalcars.views.main;

import com.example.rentalcars.security.SecurityService;
import com.example.rentalcars.security.SecurityUtils;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.service.EmployeeService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.customer.*;
import com.example.rentalcars.views.main.employee.*;
import com.example.rentalcars.views.main.manager.*;
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
    private final SecurityService securityService;
    private final UserService userService;
    private final String loggedUserName;
    private List<Class<? extends Component>> allowedViews;

    public MainLayout(CompanyService companyService, SecurityService securityService, UserService userService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.securityService = securityService;
        this.userService = userService;
        loggedUserName = userService.getNameOfLoggedUser();
        this.allowedViews = new ArrayList<>();
        createHeader();
        createDrawer();
        addAllowedViews();
    }

    private void addAllowedViews() {
        //Widoki dostępne dla wszystkich
        //

        //Widoki dla Managera
        if (SecurityUtils.isUserAdmin() || SecurityUtils.isUserManager()){
                allowedViews.add(RentalView.class);
                allowedViews.add(ReturnView.class);
                allowedViews.add(CarView.class);
                allowedViews.add(CarFleetView.class);
                allowedViews.add(CompanyManagementView.class);
                allowedViews.add(CustomerManagementView.class);
                allowedViews.add(DepartmentCarsManagementView.class);
                allowedViews.add(DepartmentEmployeesManagementView.class);
                allowedViews.add(DepartmentManagementView.class);
                allowedViews.add(EmployeeManagementView.class);
                allowedViews.add(HistoryView.class);
                allowedViews.add(DashboardView.class);
                allowedViews.add(UserManagementView.class);
                allowedViews.add(EmployeeDataView.class);
        }

        //Widoki dla Pracownika
        if (SecurityUtils.isUserEmployee()){
                allowedViews.add(RentalView.class);
                allowedViews.add(ReturnView.class);
                allowedViews.add(CarView.class);
                allowedViews.add(CarFleetView.class);
                allowedViews.add(CustomerManagementView.class);
                allowedViews.add(DepartmentCarsManagementView.class);
                allowedViews.add(HistoryView.class);
                allowedViews.add(EmployeeDataView.class);
        }

        //Widoki dla Klienta
        if (SecurityUtils.isUserCustomer()){
            allowedViews.add(ReservationView.class);
            allowedViews.add(CarFleetView.class);
            allowedViews.add(CustomerReservationsView.class);
            allowedViews.add(CustomerDataView.class);
            allowedViews.add(DepartmentView.class);
        }

    }

    public void navigateTo(Class<? extends Component> viewClass){
        getUI().ifPresent(ui->ui.navigate(viewClass));
    }

    private void createHeader() {
        H1 companyName = new H1("Wypożyczalnia samochodów " + companyService.getCompanyName());
        companyName.addClassNames("text-l", "m-m");

        Button loginButton = new Button("Zaloguj się", e -> UI.getCurrent().navigate("login"));
        Button registerButton = new Button("Zarejestruj się");
        registerButton.addClickListener(e -> UI.getCurrent().navigate("register"));
        Button logoutButton = new Button("Wyloguj " + loggedUserName, e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), companyName, loginButton, registerButton, logoutButton);

        if (userService.isUserLogged()){
            registerButton.setVisible(false);
            logoutButton.setVisible(true);
            loginButton.setVisible(false);
        } else {
            registerButton.setVisible(true);
            logoutButton.setVisible(false);
            loginButton.setVisible(true);
        }

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(companyName);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }
    private void createDrawer() {
        RouterLink carView = new RouterLink("Samochody", CarView.class);
        carView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink rentalView = new RouterLink("Wypożycz", RentalView.class);
        rentalView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink companyManagementView = new RouterLink("Konfiguracja", CompanyManagementView.class);
        companyManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink employeeManagementView = new RouterLink("Pracownicy", EmployeeManagementView.class);
        employeeManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customerManagementView = new RouterLink("Klienci", CustomerManagementView.class);
        customerManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reservationView = new RouterLink("Zarezerwuj", ReservationView.class);
        reservationView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentManagementView = new RouterLink("Oddziały", DepartmentManagementView.class);
        departmentManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink returnView = new RouterLink("Zwróć", ReturnView.class);
        returnView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentEmployeesManagementView = new RouterLink("Pracownicy oddziałów", DepartmentEmployeesManagementView.class);
        departmentEmployeesManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentCarsManagementView = new RouterLink("Samochody oddziałów", DepartmentCarsManagementView.class);
        departmentCarsManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customerDataView = new RouterLink("Moje dane", CustomerDataView.class);
        customerDataView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink employeeDataView = new RouterLink("Moje dane", EmployeeDataView.class);
        employeeDataView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customerReservationsView = new RouterLink("Moje rezerwacje", CustomerReservationsView.class);
        customerReservationsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink historyView = new RouterLink("Rezerwacje, wypożyczenia i zwroty", HistoryView.class);
        historyView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink dashboardView = new RouterLink("Dashboard", DashboardView.class);
        dashboardView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink userManagementView = new RouterLink("Użytkownicy", UserManagementView.class);
        userManagementView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentView = new RouterLink("Oddziały", DepartmentView.class);
        departmentView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink carFleetView = new RouterLink("Flota", CarFleetView.class);
        carFleetView.setHighlightCondition(HighlightConditions.sameLocation());

        if (SecurityUtils.isUserAdmin() || SecurityUtils.isUserManager()){
            addToDrawer(new VerticalLayout(
                carView,
                carFleetView,
                rentalView,
                returnView,
                employeeManagementView,
                customerManagementView,
                departmentManagementView,
                departmentEmployeesManagementView,
                departmentCarsManagementView,
                companyManagementView,
                historyView,
                dashboardView,
                userManagementView,
                employeeDataView
            ));
        }

        if (SecurityUtils.isUserEmployee()){
            addToDrawer(new VerticalLayout(
                carView,
                carFleetView,
                rentalView,
                returnView,
                customerManagementView,
                departmentCarsManagementView,
                historyView,
                employeeDataView
            ));
        }

        if (SecurityUtils.isUserCustomer()){
            addToDrawer(new VerticalLayout(
                carFleetView,
                reservationView,
                customerReservationsView,
                customerDataView,
                departmentView
            ));
        }

    }

}
