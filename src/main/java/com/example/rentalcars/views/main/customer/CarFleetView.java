package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
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
@CssImport(value = "./styles/styles.css")
public class CarFleetView extends Div {
    private final CarService carService;

    public CarFleetView(CarService carService) {
        this.carService = carService;
        List<CarModel> cars = carService.getCarList1();
        VirtualList<CarModel> list = new VirtualList<>();
        list.setItems(cars);
        list.setRenderer(carRenderer);
        list.getElement().getStyle().set("height", "800px");
        add(list);
    }

    private ComponentRenderer<Component, CarModel> carRenderer = new ComponentRenderer<>(
            car -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);
                Avatar avatar = new Avatar();
                String carImagesPath = "cars/";
                avatar.setImage(carImagesPath + car.getPictureUrl());
                avatar.setHeight("200px");
                avatar.setWidth("200px");
                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);

                Div markAndModelDiv = new Div(new Text(car.getMark() + " " + car.getModel()));
                markAndModelDiv.addClassName("large-text");
                infoLayout.add(markAndModelDiv);
                infoLayout.add(new Div(new Text("Cena:\t" + car.getPrice().toString())));
                infoLayout.add(new Div(new Text("Nadwozie:\t" + car.getBody().toString())));
                infoLayout.add(new Div(new Text("Skrzynia biegów:\t" + car.getGearbox().toString())));
                infoLayout.add(new Div(new Text("Dostępność:\t" + car.getAvailability().toString())));
                cardLayout.add(avatar, infoLayout);
                return cardLayout;
            });


}
