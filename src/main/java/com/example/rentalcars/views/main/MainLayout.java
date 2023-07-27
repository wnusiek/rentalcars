package com.example.rentalcars.views.main;

import com.example.rentalcars.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

import javax.swing.text.html.ListView;
@PermitAll
public class MainLayout extends AppLayout {
    private SecurityService securityService;
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();

    }
    private void createHeader() {
        H1 logo = new H1("Wypożyczalnia Gruz-rental");
        logo.addClassNames("text-l", "m-m");

        Button logOut = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logOut);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
    private void createDrawer() {
        RouterLink mainView = new RouterLink("Lista samochodów", MainView.class);
        mainView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink reservationsView = new RouterLink("Lista rezerwacji", ReservationsView.class);
        reservationsView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                mainView,
                reservationsView
        ));

    }


}
