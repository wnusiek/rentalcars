package com.example.rentalcars.views.main.employee;

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

    Binder<RentalModel> rentalBinder = new Binder<>(RentalModel.class);
    Binder<ReservationModel> reservationBinder = new Binder<>(ReservationModel.class);
    ComboBox<EmployeeModel> employee = new ComboBox<>("Employee");
    ComboBox<ReservationModel> reservation = new ComboBox<>("Reservation");
    DatePicker dateOfRental = new DatePicker("Date of rental");
    TextField comments = new TextField("Comments");

    Button save = new Button("Wypożycz auto");
    Button cancel = new Button("Anuluj");
    private RentalModel rentalModel;

    public RentalForm(List<EmployeeModel> employees, List<ReservationModel> reservations) {
        addClassName("rental-form");
//        rentalBinder.bindInstanceFields(this);
//        reservationBinder.forField(dateOfRental).bind(ReservationModel::getDateFrom, ReservationModel::setDateFrom);

        employee.setItems(employees);
        employee.setItemLabelGenerator(EmployeeModel::getName);
        reservation.setItems(reservations);
        reservation.setItemLabelGenerator(ReservationModel::getReservationInfo);

        rentalBinder.forField(employee).asRequired("To pole jest wymagane!").bind(RentalModel::getEmployee, RentalModel::setEmployee);
        rentalBinder.forField(reservation).asRequired("To pole jest wymagane!").bind(RentalModel::getReservation, RentalModel::setReservation);
        rentalBinder.forField(comments).bind(RentalModel::getComments, RentalModel::setComments);
        rentalBinder.forField(dateOfRental).asRequired("To pole jest wymagane!").bind(RentalModel::getDateOfRental, RentalModel::setDateOfRental);
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
//        rentalBinder.readBean(rentalModel);
    }

    private Component createButtonLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, cancel);
    }

    private void validateAndSave() {
        try{
            rentalBinder.writeBean(rentalModel);
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
//    public static class DeleteEvent extends RentalFormEvent{
//        DeleteEvent(RentalForm source, RentalModel rentalModel){
//            super(source, rentalModel);
//        }
//    }
    public static class CloseEvent extends RentalForm.RentalFormEvent {
        CloseEvent(RentalForm source) {
            super(source, null);
        }
    }
//    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener){
//        return addListener(DeleteEvent.class, listener);
//    }
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}
