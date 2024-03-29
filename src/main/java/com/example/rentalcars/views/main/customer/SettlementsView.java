package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.service.ReservationService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "settlements", layout = MainLayout.class)
@PageTitle("Rozliczenia")
@AnonymousAllowed
public class SettlementsView extends VerticalLayout {
    private final ReservationService reservationService;
//    Grid<ReservationModel> reservationGrid = new Grid<>(ReservationModel.class, false);



    public SettlementsView(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

}
