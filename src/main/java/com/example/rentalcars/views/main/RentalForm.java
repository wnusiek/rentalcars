package com.example.rentalcars.views.main;

import com.example.rentalcars.DTO.EmployeeDto;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.EmployeeRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class RentalForm extends FormLayout {
    ComboBox<EmployeeDto> employee = new ComboBox<>("Employee");
    ComboBox<ReservationModel> reservation = new ComboBox<>("Reservation");
    Button rent = new Button("Rent a car");
    Button cancel = new Button("Cancel reservation");
    DatePicker dateOfRental = new DatePicker("Date of rental");
    TextField comments = new TextField();

    public RentalForm(List<EmployeeDto> employees) {
        addClassName("rental-form");
        employee.setItems(employees);
        add(
                employee,
                reservation,
                dateOfRental,
                comments
                );
    }
    private Component createButtonLayout(){
        rent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return new HorizontalLayout(rent, cancel);
    }
}
