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
    TextField mark = new TextField("Marka");
    TextField model = new TextField("Model");
    TextField price = new TextField("Cena");
    TextField bail = new TextField("Kaucja");
    ComboBox<BodyType> body = new ComboBox<>("Nadwozie");
    ComboBox<GearboxType> gearbox = new ComboBox<>("Skrzynia biegów");
    TextField numberOfSeats = new TextField("Liczba miejsc");
    TextField numberOfDoors = new TextField("Liczba drzwi");
    ComboBox<FuelType> fuelType = new ComboBox<>("Paliwo");
    TextField trunk = new TextField("Bagażnik");
    ComboBox<CarStatus> availability = new ComboBox<>("Dostępność");
    TextField color = new TextField("Kolor");
    TextField mileage = new TextField("Przebieg");
    TextField productionDate = new TextField("Rok produkcji");
    TextField url = new TextField("Ścieżka do zdjęcia");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private CarModel carModel;

    public CarForm() {
        String requiredFieldErrorMessage = "To pole jest wymagane!";
        String wrongValueErrorMessage = "Zła wartość";
        String onlyNumbersErrorMessage = "Tylko liczby!";
        body.setItems(BodyType.values());
        fuelType.setItems(FuelType.values());
        gearbox.setItems(GearboxType.values());
        availability.setItems(CarStatus.values());
        binder.forField(mark)
                .asRequired(requiredFieldErrorMessage)
                .bind(CarModel::getMark, CarModel::setMark);
        binder.forField(model)
                .asRequired(requiredFieldErrorMessage)
                .bind(CarModel::getModel, CarModel::setModel);
        binder.forField(color)
                .asRequired(requiredFieldErrorMessage)
                .bind(CarModel::getColor, CarModel::setColor);
        binder.forField(trunk)
                .asRequired(requiredFieldErrorMessage)
                .bind(CarModel::getTrunk, CarModel::setTrunk);
        binder.forField(price)
                .asRequired(requiredFieldErrorMessage)
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter(wrongValueErrorMessage))
                .bind(CarModel::getPrice, CarModel::setPrice);
        binder.forField(bail)
                .asRequired(requiredFieldErrorMessage)
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter(wrongValueErrorMessage))
                .bind(CarModel::getBail, CarModel::setBail);
        binder.forField(productionDate)
                .asRequired(requiredFieldErrorMessage)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(onlyNumbersErrorMessage))
                .bind(CarModel::getProductionDate, CarModel::setProductionDate);
        binder.forField(mileage)
                .asRequired(requiredFieldErrorMessage)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(onlyNumbersErrorMessage))
                .bind(CarModel::getMileage, CarModel::setMileage);
        binder.forField(numberOfDoors)
                .asRequired(requiredFieldErrorMessage)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(onlyNumbersErrorMessage))
                .bind(CarModel::getNumberOfDoors, CarModel::setNumberOfDoors);
        binder.forField(numberOfSeats)
                .asRequired(requiredFieldErrorMessage)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(onlyNumbersErrorMessage))
                .bind(CarModel::getNumberOfSeats, CarModel::setNumberOfSeats);
        binder.forField(url)
                .asRequired(requiredFieldErrorMessage)
                .bind(CarModel::getPictureUrl, CarModel::setPictureUrl);
        binder.bindInstanceFields(this);
        addClassName("car-form");
        add(
                mark,
                model,
                price,
                bail,
                body,
                gearbox,
                numberOfSeats,
                numberOfDoors,
                fuelType,
                trunk,
                availability,
                color,
                mileage,
                productionDate,
                url,
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

        return new HorizontalLayout(save, cancel);
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
