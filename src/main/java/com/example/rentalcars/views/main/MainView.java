package com.example.rentalcars.views.main;


import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.vaadinService.GruzRentalVaadinService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@Route(value = "")
@PageTitle("Gruz Car Rental")
public class MainView extends VerticalLayout {

    private final GruzRentalVaadinService service;
    Grid<CarDto> grid = new Grid<>(CarDto.class);
    TextField filterText = new TextField();

    public MainView(GruzRentalVaadinService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);

        updateCarList();
    }

    private void updateCarList() {
        grid.setItems(service.findCarsByMark(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("cars-grid");
        grid.setSizeFull();
        grid.setColumns("mark", "model", "body", "color", "fuelType", "gearbox", "price", "availability");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by mark...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCarList());

        Button addCarButton = new Button("Add car");

        var toolbar = new HorizontalLayout(filterText, addCarButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


}