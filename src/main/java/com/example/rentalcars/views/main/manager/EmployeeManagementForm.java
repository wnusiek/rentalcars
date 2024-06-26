package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.enums.EmployeePosition;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.UserModel;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PermitAll
public class EmployeeManagementForm extends FormLayout {
    Binder<EmployeeModel> binder = new BeanValidationBinder<>(EmployeeModel.class);

    TextField firstName = new TextField("Imię");
    TextField lastName = new TextField("Nazwisko");
    ComboBox<EmployeePosition> position = new ComboBox<>("Stanowisko");
    ComboBox<UserModel> user = new ComboBox<>("Użytkownik");

    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");
    Button cancel = new Button("Anuluj");
    private EmployeeModel employeeModel;

    public EmployeeManagementForm(List<UserModel> users) {
        position.setItems(EmployeePosition.values());
        user.setItems(users);
        user.setItemLabelGenerator(UserModel::getName);
        binder.bindInstanceFields(this);
        addClassName("employee-form");
        add(
                firstName,
                lastName,
                position,
                user,
                createButtonLayout()
        );
    }

    public void setEmployee(EmployeeModel employeeModel){
        this.employeeModel = employeeModel;
        binder.readBean(employeeModel);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event ->  fireEvent(new DeleteEvent(this, employeeModel)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(employeeModel);
            fireEvent(new SaveEvent(this, employeeModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    //Events
    public static abstract class EmployeeFormEvent extends ComponentEvent<EmployeeManagementForm> {
        private EmployeeModel employeeModel;
        protected EmployeeFormEvent(EmployeeManagementForm source, EmployeeModel employeeModel) {
            super(source, false);
            this.employeeModel = employeeModel;
        }
        public EmployeeModel getEmployee() {
            return employeeModel;
        }
    }
    public static class SaveEvent extends EmployeeFormEvent {
        SaveEvent(EmployeeManagementForm source, EmployeeModel employeeModel) {
            super(source, employeeModel);
        }
    }
    public static class DeleteEvent extends EmployeeFormEvent {
        DeleteEvent(EmployeeManagementForm source, EmployeeModel employeeModel) {
            super(source, employeeModel);
        }
    }
    public static class CloseEvent extends EmployeeFormEvent {
        CloseEvent(EmployeeManagementForm source) {
            super(source, null);
        }
    }
    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }


}
