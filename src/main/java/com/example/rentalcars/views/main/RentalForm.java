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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

import java.util.List;
@PermitAll
public class RentalForm extends FormLayout {

    Binder<RentalModel> binder = new Binder<>(RentalModel.class);
    Binder<ReservationModel> binder1 = new Binder<>(ReservationModel.class);

    ComboBox<EmployeeModel> employee = new ComboBox<>("Employee");
    ComboBox<ReservationModel> reservation = new ComboBox<>("Reservation");
    Button rent = new Button("Rent a car");
    Button cancel = new Button("Cancel reservation");
    DatePicker dateOfRental = new DatePicker("Date of rental");
    TextField comments = new TextField("Comments");
    private RentalModel rentalModel;

    public RentalForm(List<EmployeeModel> employees, List<ReservationModel> reservations) {
        addClassName("rental-form");
        binder1.forField(dateOfRental).bind(ReservationModel::getDateFrom, ReservationModel::setDateFrom);

        employee.setItems(employees);
        employee.setItemLabelGenerator(EmployeeModel::getName);
        reservation.setItems(reservations);
        reservation.setItemLabelGenerator(ReservationModel::getReservationInfo);
        binder.forField(employee).bind(RentalModel::getEmployee, RentalModel::setEmployee);
        binder.forField(reservation).bind(RentalModel::getReservation, RentalModel::setReservation);
        binder.forField(comments).bind(RentalModel::getComments, RentalModel::setComments);
        binder.forField(dateOfRental).bind(RentalModel::getDateOfRental, RentalModel::setDateOfRental);
        add(
                employee,
                reservation,
                dateOfRental,
                comments,
                createButtonLayout()
                );
    }

//    public void setRental(ReservationModel reservationModel) {
//        this.reservationModel = reservationModel;
//    }

    private Component createButtonLayout(){
        rent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        rent.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));

        return new HorizontalLayout(rent, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(rentalModel);
            fireEvent(new SaveEvent(this, rentalModel));
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
    public static class SaveEvent extends RentalForm.RentalFormEvent {
        SaveEvent(RentalForm source, RentalModel rentalModel) {
            super(source, rentalModel);
        }
    }
    public static class CancelEvent extends RentalForm.RentalFormEvent {
        CancelEvent(RentalForm source) {
            super(source, null);
        }
    }
    public Registration addRentListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
        return addListener(CancelEvent.class, listener);
    }

}
