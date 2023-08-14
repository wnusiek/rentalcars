package com.example.rentalcars.views.main;

import com.example.rentalcars.security.SecurityService;
import com.example.rentalcars.security.SecurityUtils;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.customer.ReservationView;
import com.example.rentalcars.views.main.customer.CustomerView;
import com.example.rentalcars.views.main.employee.*;
import com.example.rentalcars.views.main.manager.CompanyView;
import com.example.rentalcars.views.main.manager.DepartmentEmployeesView;
import com.example.rentalcars.views.main.manager.DepartmentView;
import com.example.rentalcars.views.main.manager.EmployeeView;
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
        allowedViews.add(ReservationView.class);
//        allowedViews.add();
//        allowedViews.add();


        //Widoki dla admina
        if (SecurityUtils.isUserAdmin()){
            allowedViews.add(RentalView.class);
            allowedViews.add(ReturnView.class);
            allowedViews.add(CarView.class);
            allowedViews.add(CompanyView.class);
            allowedViews.add(CustomerManagementView.class);
            allowedViews.add(DepartmentCarsView.class);
            allowedViews.add(DepartmentEmployeesView.class);
            allowedViews.add(DepartmentView.class);
            allowedViews.add(EmployeeView.class);
            allowedViews.add(RentalListView.class);
            allowedViews.add(ReservationListView.class);
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

        RouterLink companyView = new RouterLink("Konfiguracja", CompanyView.class);
        companyView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink employeeView = new RouterLink("Pracownicy", EmployeeView.class);
        employeeView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customerListView = new RouterLink("Klienci", CustomerManagementView.class);
        customerListView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reservationListView = new RouterLink("Pokaż rezerwacje", ReservationListView.class);
        reservationListView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reservationView = new RouterLink("Zarezerwuj", ReservationView.class);
        reservationView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentView = new RouterLink("Oddziały", DepartmentView.class);
        departmentView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink rentalListView = new RouterLink("Wypożyczenia", RentalListView.class);
        rentalListView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink returnView = new RouterLink("Zwróć", ReturnView.class);
        returnView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink returnListView = new RouterLink("Zwroty", ReturnListView.class);
        returnListView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentEmployeesView = new RouterLink("Pracownicy oddziałów", DepartmentEmployeesView.class);
        departmentEmployeesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink departmentCarsView = new RouterLink("Samochody oddziałów", DepartmentCarsView.class);
        departmentCarsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customerView = new RouterLink("Widok klienta", CustomerView.class);
        customerView.setHighlightCondition(HighlightConditions.sameLocation());

        if (SecurityUtils.isUserAdmin()){
            addToDrawer(new VerticalLayout(

                    carView,
                    reservationView,
                    reservationListView,
                    rentalView,
                    rentalListView,
                    returnView,
                    returnListView,
                    employeeView,
                    customerListView,
                    departmentView,
                    departmentEmployeesView,
                    departmentCarsView,
                    customerView,
                    companyView
                    ));
        }

        if (SecurityUtils.isUserRegular()){
            addToDrawer(new VerticalLayout(
                    customerView,
                    reservationView
            ));
        }



    }


}
