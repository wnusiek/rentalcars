package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ReservationForm extends FormLayout {
    Binder<ReservationModel> binder = new Binder<>(ReservationModel.class);
    ComboBox<CustomerModel> customer = new ComboBox<>("Klient");
    ComboBox<CarModel> car = new ComboBox<>("Samochów");
    DatePicker dateFrom = new DatePicker("Data od");
    DatePicker dateTo = new DatePicker("Data do");
    ComboBox<DepartmentModel> receptionVenue = new ComboBox<>("Oddział wypożyczenia auta");
    ComboBox<DepartmentModel> returnVenue = new ComboBox<>("Oddział zwrotu auta");


    Button makeReservationButton = new Button("Zarezerwuj");
    Button deleteButton = new Button("Delete");
    Button cancelButton = new Button("Anuluj");
    private ReservationModel reservationModel;

    public ReservationForm(List<DepartmentModel> departments, List<CarModel> cars, List<CustomerModel> customers) {
        binder.bindInstanceFields(this);
        //        receptionVenue.setItems();
        receptionVenue.setItemLabelGenerator(DepartmentModel::getCity);
        returnVenue.setItems(departments.stream().toList());
        returnVenue.setItemLabelGenerator(DepartmentModel::getCity);

        binder.forField(dateFrom).bind(ReservationModel::getDateFrom, ReservationModel::setDateFrom);
        binder.forField(dateTo).bind(ReservationModel::getDateTo, ReservationModel::setDateTo);
        binder.forField(receptionVenue).bind(ReservationModel::getReceptionVenue, ReservationModel::setReceptionVenue);
        binder.forField(returnVenue).bind(ReservationModel::getReturnVenue, ReservationModel::setReturnVenue);
        addClassName("reservation-form");
        add(
                dateFrom,
                dateTo,
                receptionVenue,
                returnVenue,
                createButtonLayout()
        );
    }

    public void setReservation(ReservationModel reservationModel){
        this.reservationModel = reservationModel;
        binder.readBean(reservationModel);
    }

    private Component createButtonLayout() {
        makeReservationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        makeReservationButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event ->  fireEvent(new ReservationForm.DeleteEvent(this, reservationModel)));
        cancelButton.addClickListener(event -> fireEvent(new ReservationForm.CloseEvent(this)));

        makeReservationButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(makeReservationButton, cancelButton);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(reservationModel);
            fireEvent(new ReservationForm.SaveEvent(this, reservationModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //Events
    public static abstract class ReservationFormEvent extends ComponentEvent<ReservationForm> {
        private ReservationModel reservationModel;
        protected ReservationFormEvent(ReservationForm source, ReservationModel reservationModel) {
            super(source, false);
            this.reservationModel = reservationModel;
        }
        public ReservationModel getReservation() {
            return reservationModel;
        }
    }
    public static class SaveEvent extends ReservationForm.ReservationFormEvent {
        SaveEvent(ReservationForm source, ReservationModel reservationModel) {
            super(source, reservationModel);
        }
    }
    public static class DeleteEvent extends ReservationForm.ReservationFormEvent {
        DeleteEvent(ReservationForm source, ReservationModel reservationModel) {
            super(source, reservationModel);
        }
    }
    public static class CloseEvent extends ReservationForm.ReservationFormEvent {
        CloseEvent(ReservationForm source) {
            super(source, null);
        }
    }
    public Registration addDeleteListener(ComponentEventListener<ReservationForm.DeleteEvent> listener) {
        return addListener(ReservationForm.DeleteEvent.class, listener);
    }
    public Registration addSaveListener(ComponentEventListener<ReservationForm.SaveEvent> listener) {
        return addListener(ReservationForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<ReservationForm.CloseEvent> listener) {
        return addListener(ReservationForm.CloseEvent.class, listener);
    }
}
