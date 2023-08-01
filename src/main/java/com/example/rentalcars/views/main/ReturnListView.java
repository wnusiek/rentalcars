package com.example.rentalcars.views.main;

import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.ReturnService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
@Route(value = "returns", layout = MainLayout.class)
@PageTitle("Lista zwrotów")
@PermitAll
public class ReturnListView extends VerticalLayout {
    private final ReturnService returnService;
    Grid<ReturnModel> returnGrid = new Grid<>(ReturnModel.class, false);

    public ReturnListView(ReturnService returnService) {
        this.returnService = returnService;
        addClassName("returns-view");
        setSizeFull();
        configureGrid();
        add(
                getContent()
        );
        updateReturnList();
    }

    private void configureGrid(){
        returnGrid.addClassNames("returns-grid");
        returnGrid.setSizeFull();
        returnGrid.addColumn("employee.firstName").setHeader("Imię pracownika");
        returnGrid.addColumn("employee.lastName").setHeader("Nazwisko pracownika");
        returnGrid.addColumn("reservation.car.mark").setHeader("Marka samochodu");
        returnGrid.addColumn("reservation.car.model").setHeader("Model samochodu");
        returnGrid.addColumn("reservation.dateFrom").setHeader("Data rezerwacji od");
        returnGrid.addColumn("reservation.dateTo").setHeader("Data rezerwacji do");
        returnGrid.addColumn("dateOfReturn").setHeader("Data zwrotu");
        returnGrid.addColumn("reservation.customer.firstName").setHeader("Imię Klienta");
        returnGrid.addColumn("reservation.customer.lastName").setHeader("Nazwisko klienta");
        returnGrid.addColumn("comments").setHeader("Konetarz");

        returnGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private Component getContent(){
        HorizontalLayout content = new HorizontalLayout(returnGrid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void updateReturnList(){
        returnGrid.setItems(returnService.getReturnModelList());
    }
}
