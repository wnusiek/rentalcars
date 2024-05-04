package com.example.rentalcars.views.main.employee;

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
    TextField filterText = new TextField();
    ComboBox<GearboxType> gearboxTypeComboBox = new ComboBox<>("Skrzynia biegów");
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

        filterText.setPlaceholder("Filtruj po marce lub modelu...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCarList());

        gearboxTypeComboBox.setPlaceholder("Wybierz typ skrzyni...");
        gearboxTypeComboBox.setItems(GearboxType.values());
        gearboxTypeComboBox.setClearButtonVisible(true);
        gearboxTypeComboBox.addValueChangeListener(e -> updateCarList());

        var toolbar = new HorizontalLayout(filterText, gearboxTypeComboBox, addCarButton);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
    }

    private void addCar() {
        carGrid.asSingleSelect().clear();
        editCar(new CarModel());
    }

    private void updateCarList() {
//        carGrid.setItems(carService.getCarList1());
//        carGrid.setItems(carService.findCarsByMark(filterText.getValue()));
        carGrid.setItems(carService.findWithFilter(filterText.getValue()));
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
        carGrid.addColumn("numberOfSeats").setHeader("Liczba miejsc");
        carGrid.addColumn("numberOfDoors").setHeader("Liczba drzwi");
        carGrid.addColumn("fuelType").setHeader("Paliwo");
        carGrid.addColumn("trunk").setHeader("Bagażnik");
        carGrid.addColumn("availability").setHeader("Dostępność");
        carGrid.addColumn("color").setHeader("Kolor");
        carGrid.addColumn("mileage").setHeader("Przebieg");
        carGrid.addColumn("productionDate").setHeader("Rok produkcji");

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