package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "carView", layout =  MainLayout.class)
@PageTitle("Samochody")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
public class CarView extends VerticalLayout {
    private final CarService carService;
    Grid<CarModel> carGrid = new Grid<>(CarModel.class, false);
    TextField filterText = new TextField("Marka lub model");
    ComboBox<GearboxType> gearboxType = new ComboBox<>("Skrzynia biegów");
    ComboBox<FuelType> fuelType = new ComboBox<>("Paliwo");
    ComboBox<BodyType> bodyType = new ComboBox<>("Nadwozie");
    CarForm carForm = new CarForm();

    public CarView(CarService carService) {
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
        Button addCarButton = new Button("Dodaj samochód");
        addCarButton.addClickListener(e->addCar());
        Button clearFiltersButton = new Button("Wyczyść filtry");
        clearFiltersButton.addClickListener(e -> clearFilters());

        filterText.setPlaceholder("Wpisz markę lub model...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCarList());

        bodyType.setPlaceholder("Wybierz typ nadwozia...");
        bodyType.setClearButtonVisible(true);
        bodyType.setItems(BodyType.values());
        bodyType.addValueChangeListener(e -> updateCarList());

        gearboxType.setPlaceholder("Wybierz typ skrzyni...");
        gearboxType.setClearButtonVisible(true);
        gearboxType.setItems(GearboxType.values());
        gearboxType.addValueChangeListener(e -> updateCarList());

        fuelType.setPlaceholder("Wybierz typ paliwa...");
        fuelType.setClearButtonVisible(true);
        fuelType.setItems(FuelType.values());
        fuelType.addValueChangeListener(e -> updateCarList());

        var toolbar = new HorizontalLayout(
                filterText,
                bodyType,
                gearboxType,
                fuelType,
                clearFiltersButton,
                addCarButton);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
    }

    private void clearFilters() {
        filterText.setValue("");
        bodyType.setValue(null);
        gearboxType.setValue(null);
        fuelType.setValue(null);
    }

    private void addCar() {
        carGrid.asSingleSelect().clear();
        editCar(new CarModel());
    }

    private void updateCarList() {
        carGrid.setItems(carService.findWithFilter(
                filterText.getValue(),
                bodyType.getValue(),
                gearboxType.getValue(),
                fuelType.getValue()));
    }

    private void configureGrid() {
        carGrid.addClassNames("cars-grid");
        carGrid.setSizeFull();
        carGrid.addColumn("mark").setHeader("Marka");
        carGrid.addColumn("model").setHeader("Model");
        carGrid.addColumn("price").setHeader("Cena");
        carGrid.addColumn("bail").setHeader("Kaucja");
        carGrid.addColumn("body").setHeader("Nadwozie");
        carGrid.addColumn("gearbox").setHeader("Skrzynia biegów");
        carGrid.addColumn("numberOfSeats").setHeader("Miejsc");
        carGrid.addColumn("numberOfDoors").setHeader("Drzwi");
        carGrid.addColumn("fuelType").setHeader("Paliwo");
        carGrid.addColumn("trunk").setHeader("Bagażnik");
        carGrid.addColumn("availability").setHeader("Dostępność");
        carGrid.addColumn("color").setHeader("Kolor");
        carGrid.addColumn("mileage").setHeader("Przebieg");
        carGrid.addColumn("productionDate").setHeader("Rok produkcji");
        carGrid.addColumn("pictureUrl").setHeader("Ścieżka do zdjęcia");
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