package com.example.rentalcars.views.main;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.vaadin.flow.component.Component;
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

public class ReservationForm extends FormLayout {
    Binder<ReservationModel> binder = new BeanValidationBinder<>(ReservationModel.class);
    DatePicker dateFrom = new DatePicker("Data od");
    DatePicker dateTo = new DatePicker("Data do");
    ComboBox<DepartmentModel> receptionVenue = new ComboBox<>("Oddział wypożyczenia auta");
    ComboBox<DepartmentModel> returnVenue = new ComboBox<>("Oddział zwrotu auta");
    Button makeReservationButton = new Button("Zarezerwuj");
    Button cancel = new Button("Anuluj");
    private ReservationModel reservationModel;

    public ReservationForm() {
        binder.bindInstanceFields(this);
        addClassName("reservation-form");
        add(dateFrom, dateTo, returnVenue, returnVenue, createButtonLayout()
        );
    }

    public void setReservation(ReservationModel reservationModel){
        this.reservationModel = reservationModel;
        binder.readBean(reservationModel);
    }

    private Component createButtonLayout() {
        makeReservationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        makeReservationButton.addClickListener(event -> validateAndSave());
//        cancel.addClickListener(event -> fireEvent(new ReservationForm(this)));

        makeReservationButton.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(makeReservationButton, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(reservationModel);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
