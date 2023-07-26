package com.example.rentalcars.views.main;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import javax.swing.text.html.ListView;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();

    }
    private void createHeader() {
        H1 logo = new H1("Wypożyczalnia Gruz-rental");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
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
