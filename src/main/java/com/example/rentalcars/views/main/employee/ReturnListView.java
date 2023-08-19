package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.ReturnService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "returnlist", layout = MainLayout.class)
@PageTitle("Lista zwrotów")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class ReturnListView extends VerticalLayout {

    private final ReturnService returnService;
    Grid<ReturnModel> returnGrid = new Grid<>(ReturnModel.class, false);

    public ReturnListView(ReturnService returnService) {
        this.returnService = returnService;
        setSizeFull();
        configureGrid();
        add(returnGrid);
        returnGrid.setItems(returnService.getReturnModelList());
    }

    private void configureGrid(){
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
        returnGrid.addColumn("comments").setHeader("Komentarz");
        returnGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
