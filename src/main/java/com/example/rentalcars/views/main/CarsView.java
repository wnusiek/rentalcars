package com.example.rentalcars.views.main;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.vaadinService.GruzRentalVaadinService;
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
    Grid<CarModel> grid = new Grid<>(CarModel.class);
    TextField filterText = new TextField();
    CarForm form = new CarForm();

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
        form.setCar(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new CarForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveCar);
        form.addDeleteListener(this::deleteCar);
        form.addCloseListener(event -> closeEditor());
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
        grid.asSingleSelect().clear();
        editCar(new CarModel());
    }

    private void updateCarList() {
        grid.setItems(carService.getCarList1());
    }

    private void configureGrid() {
        grid.addClassNames("cars-grid");
        grid.setSizeFull();
        grid.setColumns("mark", "model", "body", "color", "fuelType", "gearbox", "price", "availability");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void editCar(CarModel carModel) {
        if(carModel == null){
            closeEditor();
        }else {
            form.setCar(carModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }


}