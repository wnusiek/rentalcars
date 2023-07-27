package com.example.rentalcars.views.main;

import com.example.rentalcars.repository.CompanyRepository;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    private final CompanyRepository companyRepository;

    public MainLayout(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        createHeader();
        createDrawer();

    }
    private void createHeader() {
        H1 companyName = new H1(companyRepository.findById(1l).get().getCompanyName());
        companyName.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), companyName);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(companyName);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
    private void createDrawer() {
        RouterLink mainView = new RouterLink("Lista samochodów", MainView.class);
        mainView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reservationsView = new RouterLink("Lista rezerwacji", ReservationsView.class);
        reservationsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink companyView = new RouterLink("Konfiguracja", CompanyView.class);
        companyView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink employeesView = new RouterLink("Lista pracowników", EmployeesView.class);
        employeesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink customersView = new RouterLink("Lista klientów", CustomerListView.class);
        customersView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink addReservationView = new RouterLink("Tworzenie rezerwacji", AddReservationView.class);
        addReservationView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                mainView,
                reservationsView,
                companyView,
                employeesView,
                customersView,
                addReservationView
        ));

    }


}
