package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class CarForm extends FormLayout {
    Binder<CarModel> binder = new Binder<>(CarModel.class);
    Binder<CarModel> enumBinder = new Binder<>(CarModel.class);

    TextField mark = new TextField("Marka");
    TextField model = new TextField("Model");
    ComboBox<BodyType> body = new ComboBox<>("Nadwozie");
    TextField color = new TextField("Kolor");
    ComboBox<FuelType> fuelType = new ComboBox<>("Paliwo");
    ComboBox<GearboxType> gearbox = new ComboBox<>("Skrzynia biegów");
    TextField price = new TextField("Cena");
    ComboBox<CarStatus> availability = new ComboBox<>("Dostępność");
    TextField mileage = new TextField("Przebieg");
    TextField productionDate = new TextField("Rok produkcji");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private CarModel carModel;

    public CarForm() {

        body.setItems(BodyType.values());
        fuelType.setItems(FuelType.values());
        gearbox.setItems(GearboxType.values());
        availability.setItems(CarStatus.values());
        binder.forField(mark).asRequired("To pole jest wymagane!").bind(CarModel::getMark, CarModel::setMark);
        binder.forField(model).asRequired("To pole jest wymagane!").bind(CarModel::getModel, CarModel::setModel);
        //binder.bind(body,CarModel::getBody, CarModel::setBody);
        binder.forField(color).asRequired("To pole jest wymagane!").bind(CarModel::getColor, CarModel::setColor);
        //binder.bind(fuelType,CarModel::getFuelType, CarModel::setFuelType);
        //binder.bind(gearbox,CarModel::getGearbox, CarModel::setGearbox);
        binder.forField(price).asRequired("To pole jest wymagane!").withNullRepresentation("").withConverter(new StringToBigDecimalConverter("zła wartość")).bind(CarModel::getPrice, CarModel::setPrice);
        //binder.bind(availability,CarModel::getAvailability, CarModel::setAvailability);
        binder.forField(productionDate).asRequired("To pole jest wymagane!").withNullRepresentation("").withConverter(new StringToIntegerConverter("tylko liczby!")).bind(CarModel::getProductionDate, CarModel::setProductionDate);
        binder.forField(mileage).asRequired("To pole jest wymagane!").withNullRepresentation("").withConverter(new StringToIntegerConverter("tylko liczby!")).bind(CarModel::getMileage, CarModel::setMileage);
        binder.bindInstanceFields(this);

        addClassName("car-form");
        add(
                mark,
                model,
                body,
                color,
                fuelType,
                gearbox,
                price,
                availability,
                mileage,
                productionDate,
                createButtonLayout()
        );
    }

    public void setCar(CarModel carModel){
        this.carModel = carModel;
        binder.readBean(carModel);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event ->  fireEvent(new CarForm.DeleteEvent(this, carModel)));
        cancel.addClickListener(event -> fireEvent(new CarForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(carModel);
            fireEvent(new CarForm.SaveEvent(this, carModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //Events
    public static abstract class CarFormEvent extends ComponentEvent<CarForm> {
        private CarModel carModel;
        protected CarFormEvent(CarForm source, CarModel carModel) {
            super(source, false);
            this.carModel = carModel;
        }
        public CarModel getCar() {
            return carModel;
        }
    }
    public static class SaveEvent extends CarForm.CarFormEvent {
        SaveEvent(CarForm source, CarModel carModel) {
            super(source, carModel);
        }
    }
    public static class DeleteEvent extends CarForm.CarFormEvent {
        DeleteEvent(CarForm source, CarModel carModel) {
            super(source, carModel);
        }
    }
    public static class CloseEvent extends CarForm.CarFormEvent {
        CloseEvent(CarForm source) {
            super(source, null);
        }
    }
    public Registration addDeleteListener(ComponentEventListener<CarForm.DeleteEvent> listener) {
        return addListener(CarForm.DeleteEvent.class, listener);
    }
    public Registration addSaveListener(ComponentEventListener<CarForm.SaveEvent> listener) {
        return addListener(CarForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CarForm.CloseEvent> listener) {
        return addListener(CarForm.CloseEvent.class, listener);
    }



}
