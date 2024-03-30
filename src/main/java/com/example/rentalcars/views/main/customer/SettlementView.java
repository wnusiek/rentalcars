package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.SettlementModel;
import com.example.rentalcars.service.SettlementService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "settlements", layout = MainLayout.class)
@PageTitle("Rozliczenia")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class SettlementView extends VerticalLayout {
    private final SettlementService settlementService;
    Grid<SettlementModel> grid = new Grid<>(SettlementModel.class, false);

    public SettlementView(SettlementService settlementService) {
        this.settlementService = settlementService;
        setSizeFull();
        configureGrid();
        add(getContent());
        updateSettlementsList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateSettlementsList() {
        grid.setItems(settlementService.getSettlementByCustomer());
    }

    private void configureGrid() {
        grid.addClassNames("settlements-grid");
        grid.setSizeFull();
        grid.addColumn("car.mark").setHeader("Marka");
        grid.addColumn("car.model").setHeader("Model");
        grid.addColumn("price").setHeader("Cena");
        grid.addColumn("dateFrom").setHeader("Od");
        grid.addColumn("dateTo").setHeader("Do");
        grid.addColumn("receptionVenue.city").setHeader("Miejsce odbioru");
        grid.addColumn("returnVenue.city").setHeader("Miejsce zwrotu");
        grid.addColumn("status").setHeader("Status");
        grid.addColumn("comments").setHeader("Uwagi");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
