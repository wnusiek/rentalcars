package com.example.rentalcars.views.main;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.RentalModel;
import com.example.rentalcars.model.ReservationModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class RentalForm extends FormLayout {
    Binder<RentalModel> binder = new BeanValidationBinder<>(RentalModel.class);
    ComboBox<EmployeeModel> employee = new ComboBox<>("Employee");
    ComboBox<ReservationModel> reservation = new ComboBox<>("Reservation");
    Button rent = new Button("Rent a car");
    Button cancel = new Button("Cancel reservation");
    DatePicker dateOfRental = new DatePicker("Date of rental");
    TextField comments = new TextField("Comments");
    private RentalModel rentalModel;

    public RentalForm(List<EmployeeModel> employees, List<ReservationModel> reservations) {
        addClassName("rental-form");
        binder.bindInstanceFields(this);

        employee.setItems(employees);
        employee.setItemLabelGenerator(EmployeeModel::getName);
        reservation.setItems(reservations);
        reservation.setItemLabelGenerator(ReservationModel::getReservationInfo);
        add(
                employee,
                reservation,
                dateOfRental,
                comments,
                createButtonLayout()
                );
    }

    public void setRental(RentalModel rentalModel) {
        this.rentalModel = rentalModel;
        binder.readBean(rentalModel);
    }
    private Component createButtonLayout(){
        rent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        rent.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this, rentalModel)));

        return new HorizontalLayout(rent, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(rentalModel);
            fireEvent(new RentEvent(this, rentalModel));
        } catch (ValidationException e){
            e.printStackTrace();
        }
    }

    //EVENTS
    public static abstract class RentalFormEvent extends ComponentEvent<RentalForm> {
        private RentalModel rentalModel;

        protected RentalFormEvent(RentalForm source, RentalModel rentalModel) {
            super(source, false);
            this.rentalModel = rentalModel;
        }

        public RentalModel getRental() {
            return rentalModel;
        }
    }
    public static class RentEvent extends RentalForm.RentalFormEvent {
        RentEvent(RentalForm source, RentalModel rentalModel) {
            super(source, rentalModel);
        }
    }
    public static class CancelEvent extends RentalForm.RentalFormEvent {
        CancelEvent(RentalForm source, RentalModel rentalModel) {
            super(source, rentalModel);
        }
    }
    public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
        return addListener(CancelEvent.class, listener);
    }
    public Registration addRentListener(ComponentEventListener<RentEvent> listener) {
        return addListener(RentEvent.class, listener);
    }

}
