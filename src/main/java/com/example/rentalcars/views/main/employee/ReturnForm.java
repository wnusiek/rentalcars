package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
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
public class ReturnForm extends FormLayout {
    Binder<ReturnModel> returnBinder = new Binder<>(ReturnModel.class);
    ComboBox<EmployeeModel> employee = new ComboBox<>("Pracownik");
    ComboBox<ReservationModel> reservation = new ComboBox<>("Rezerwacja");
    DatePicker dateOfReturn = new DatePicker("Data zwrotu");
    TextField comments = new TextField("Komentarz");
    Button save = new Button("Zwróć auto");
    Button cancel = new Button("Anuluj");
    private ReturnModel returnModel;

    public ReturnForm(List<EmployeeModel> employees, List<ReservationModel> reservations) {
        addClassName("return-form");
        employee.setItems(employees);
        employee.setItemLabelGenerator(EmployeeModel::getName);
        reservation.setItems(reservations);
        reservation.setItemLabelGenerator(ReservationModel::getReservationInfo);
        returnBinder.forField(employee).bind(ReturnModel::getEmployee, ReturnModel::setEmployee);
        returnBinder.forField(reservation).bind(ReturnModel::getReservation, ReturnModel::setReservation);
        returnBinder.forField(dateOfReturn).bind(ReturnModel::getDateOfReturn, ReturnModel::setDateOfReturn);
        returnBinder.forField(comments).bind(ReturnModel::getComments, ReturnModel::setComments);

        add(
            employee,
            reservation,
            dateOfReturn,
            comments,
            createButtonLayout()
        );
    }

    public void setReturn(ReturnModel returnModel) {
        this.returnModel = returnModel;
//        returnBinder.readBean(returnModel);
    }
    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new ReturnForm.CloseEvent(this)));

        return new HorizontalLayout(save, cancel);
    }

    private void validateAndSave() {
        try{
            returnBinder.writeBean(returnModel);
            fireEvent(new SaveEvent(this, returnModel));
        } catch (ValidationException e){
            e.printStackTrace();
        }
    }

    //EVENTS
    public static abstract class ReturnFormEvent extends ComponentEvent<ReturnForm> {
        private ReturnModel returnModel;

        protected ReturnFormEvent(ReturnForm source, ReturnModel returnModel) {
            super(source, false);
            this.returnModel = returnModel;
        }

        public ReturnModel getReturn() {
            return returnModel;
        }
    }

    public static class SaveEvent extends ReturnForm.ReturnFormEvent {
        SaveEvent(ReturnForm source, ReturnModel returnModel) {
            super(source, returnModel);
        }
    }

    public static class CloseEvent extends ReturnForm.ReturnFormEvent {
        CloseEvent(ReturnForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<ReturnForm.SaveEvent> listener) {
        return addListener(ReturnForm.SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<ReturnForm.CloseEvent> listener) {
        return addListener(ReturnForm.CloseEvent.class, listener);
    }







}
