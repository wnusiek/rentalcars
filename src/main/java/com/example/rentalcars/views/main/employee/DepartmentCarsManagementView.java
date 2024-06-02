package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.Collections;

@Route(value = "departmentCarsManagementView", layout = MainLayout.class)
@PageTitle("Samochody oddziałów")
@Secured({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
public class DepartmentCarsManagementView extends VerticalLayout {
    private final DepartmentService departmentService;
    private final CarService carService;
    Grid<CarModel> grid = new Grid<>(CarModel.class, false);
    ComboBox<DepartmentModel> departmentModelComboBox = new ComboBox<>("Wybierz oddział: ");
    ComboBox<CarModel> carComboBox = new ComboBox<>("Wybierz samochód");
    Button addButton = new Button("Dodaj samochód do oddziału");
    Button removeButton = new Button("Usuń samochód z oddziału");


    public DepartmentCarsManagementView(DepartmentService departmentService, CarService carService) {
        this.departmentService = departmentService;
        this.carService = carService;
        setSizeFull();
        configureGrid();
        add(
                getToolbar(),
                getContent()
        );
//        updateDepartmentEmployeeList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2,grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn("mark").setHeader("Marka");
        grid.addColumn("model").setHeader("Model");
        grid.addColumn("body").setHeader("Nadwozie");
        grid.addColumn("color").setHeader("Kolor");
        grid.addColumn("fuelType").setHeader("Paliwo");
        grid.addColumn("gearbox").setHeader("Skrzynia biegów");
        grid.addColumn("price").setHeader("Cena");
        grid.addColumn("bail").setHeader("Kaucja");
        grid.addColumn("availability").setHeader("Dostępność");
        grid.addColumn("mileage").setHeader("Przebieg");
        grid.addColumn("productionDate").setHeader("Data produkcji");
        grid.addColumn("numberOfSeats").setHeader("Liczba miejsc");
        grid.addColumn("numberOfDoors").setHeader("Liczba drzwi");
        grid.addColumn("trunk").setHeader("Bagażnik");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> carComboBox.setValue(e.getValue()));
    }

    private Component getToolbar(){
        departmentModelComboBox.setPlaceholder("Oddziały");
        departmentModelComboBox.setItems(departmentService.getDepartmentList());
        departmentModelComboBox.setItemLabelGenerator(DepartmentModel::getCity);
        departmentModelComboBox.setClearButtonVisible(true);
        departmentModelComboBox.addValueChangeListener(e -> updateDepartmentCarList());

        carComboBox.setPlaceholder("Samochody");
        carComboBox.setItems(carService.getCarList1());
        carComboBox.setItemLabelGenerator(CarModel::getCarInfo);
        carComboBox.setClearButtonVisible(true);

        addButton.setText("Dodaj do oddziału");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(event -> addToDepartment(carComboBox.getValue()));

        removeButton.setText("Usuń z oddziału");
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeButton.addClickListener(event -> removeFromDepartment(carComboBox.getValue()));

        HorizontalLayout toolbar = new HorizontalLayout(departmentModelComboBox, carComboBox, addButton, removeButton);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
    }

    private void updateDepartmentCarList() {
        if (departmentModelComboBox.getValue() == null){
            grid.setItems(Collections.emptyList());
        } else{
            grid.setItems(departmentService.findById(departmentModelComboBox.getValue().getId()).getCars());
        }
    }

    private void addToDepartment(CarModel carModel){
        var departmentModel = departmentModelComboBox.getValue();
        if (carModel == null ){
            Notification.show("Wybierz samochód").setPosition(Notification.Position.MIDDLE);
        } else if (departmentModelComboBox.getValue() == null) {
            Notification.show("Wybierz oddział").setPosition(Notification.Position.MIDDLE);
        } else {
            if (departmentService.isCarInDepartment(carModel.getId(), departmentModel.getId())) {
                Notification.show("samochód jest już w tym oddziale").setPosition(Notification.Position.MIDDLE);
            } else if (departmentService.isCarInAnyDepartment(carModel.getId())){
                Notification.show("samochód jest już w innym oddziale").setPosition(Notification.Position.MIDDLE);
            } else {
                departmentService.addCarToDepartment(carModel.getId(), departmentModel.getId());
                updateDepartmentCarList();
                Notification.show("Dodano samochód").setPosition(Notification.Position.MIDDLE);
            }
        }

    }

    private void removeFromDepartment(CarModel carModel){
        var departmentModel = departmentModelComboBox.getValue();
        if (carModel == null ){
            Notification.show("Wybierz samochód").setPosition(Notification.Position.MIDDLE);
        }
        else if (departmentModel == null){
            Notification.show("Wybierz oddział").setPosition(Notification.Position.MIDDLE);
        }else {
            if (departmentService.isCarInDepartment(carModel.getId(), departmentModel.getId())){
                departmentService.removeCarFromDepartment(carModel.getId(), departmentModel.getId());
                updateDepartmentCarList();
                Notification.show("Usunięto samochód").setPosition(Notification.Position.MIDDLE);
            }else {
                Notification.show("Samochodu nie ma w tym oddziale").setPosition(Notification.Position.MIDDLE);
            }
        }
    }



}
