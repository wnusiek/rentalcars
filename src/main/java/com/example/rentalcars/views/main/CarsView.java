package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@Route(value = "", layout =  MainLayout.class)
@PageTitle("Gruz Car Rental")
public class CarsView extends VerticalLayout {

    private final CarService carService;
    Grid<CarModel> carGrid = new Grid<>(CarModel.class);
    TextField filterText = new TextField();
    CarForm carForm = new CarForm();

    public CarsView(CarService carService) {
        this.carService = carService;
        addClassName("cars-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateCarList();
        closeEditor();
    }

    private void closeEditor() {
        carForm.setCar(null);
        carForm.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(carGrid, carForm);
        content.setFlexGrow(2, carGrid);
        content.setFlexGrow(1, carForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        carForm = new CarForm();
        carForm.setWidth("25em");

        carForm.addSaveListener(this::saveCar);
        carForm.addDeleteListener(this::deleteCar);
        carForm.addCloseListener(event -> closeEditor());
    }

    private void deleteCar(CarForm.DeleteEvent event) {
        carService.deleteCar(event.getCar());
        updateCarList();
        closeEditor();
    }

    private void saveCar(CarForm.SaveEvent event){
        carService.saveCar(event.getCar());
        updateCarList();
        closeEditor();
    }
    private HorizontalLayout getToolbar() {
        Button addCarButton = new Button("Add car");
        addCarButton.addClickListener(e->addCar());

        filterText.setPlaceholder("Filter by mark...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCarList());

        var toolbar = new HorizontalLayout(filterText, addCarButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCar() {
        carGrid.asSingleSelect().clear();
        editCar(new CarModel());
    }

    private void updateCarList() {
        carGrid.setItems(carService.getCarList1());
    }

    private void configureGrid() {
        carGrid.addClassNames("cars-grid");
        carGrid.setSizeFull();
        carGrid.setColumns("mark", "model", "body", "color", "fuelType", "gearbox", "price", "availability");
        carGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        carGrid.asSingleSelect().addValueChangeListener(event -> editCar(event.getValue()));
    }

    private void editCar(CarModel carModel) {
        if(carModel == null){
            closeEditor();
        }else {
            carForm.setCar(carModel);
            carForm.setVisible(true);
            addClassName("editing");
        }
    }


}