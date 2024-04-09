package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@Route(value = "reservation2View", layout = MainLayout.class)
@PageTitle("Rezerwowanie")
@AnonymousAllowed
public class Reservation2View extends Div {

    private final CarService carService;

    public Reservation2View(CarService carService) {
        this.carService = carService;
        List<CarModel> cars = carService.getCarList1();
        VirtualList<CarModel> list = new VirtualList<>();
        list.setItems(cars);
        list.setRenderer(carRenderer);
        add(list);
    }

    private ComponentRenderer<Component, CarModel> carRenderer = new ComponentRenderer<>(
            car -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Avatar avatar = new Avatar(car.getCarInfo());
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.add(new Div(new Text(car.getMark())));
                infoLayout.add(new Div(new Text(car.getModel())));
                infoLayout.add(new Div(new Text(car.getColor())));
                infoLayout.add(new Div(new Text(car.getPrice().toString())));
                infoLayout.add(new Div(new Text(car.getBody().toString())));
                infoLayout.add(new Div(new Text(car.getAvailability().toString())));

                cardLayout.add(infoLayout);

                return cardLayout;
            });


}
