package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.EmployeeModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class EmployeeDataForm extends FormLayout {
    Binder<EmployeeModel> binder = new BeanValidationBinder<>(EmployeeModel.class);
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");

    Button save = new Button("Save");
    Button cancel = new Button("Cancel");

    private EmployeeModel employeeModel;

    public EmployeeDataForm() {
        binder.bindInstanceFields(this);
        binder.forField(email).withValidator(new EmailValidator("Niepoprawny email"))
                .bind(employee -> employee.getUser().getEmail(), (employee, email) -> employee.getUser().setEmail(email));
        add(
                firstName,
                lastName,
                email,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new EmployeeDataForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, cancel);
    }

    private void validateAndSave() {
//        if(firstName.isEmpty()|| lastName.isEmpty()||phoneNumber.isEmpty()||driverLicenceNumber.isEmpty()|| email.isEmpty()
//        ||pesel.isEmpty()||city.isEmpty()||zipCode.isEmpty()){
//            Notification.show("Wszystkie pola są wymagane");
//            return;
//        }
        try {
            binder.writeBean(employeeModel);
            fireEvent(new SaveEvent(this, employeeModel));
            Notification.show("Zapisano").setPosition(Notification.Position.MIDDLE);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setEmployee(EmployeeModel employeeModel){
        this.employeeModel = employeeModel;
        binder.readBean(employeeModel);
    }

    //Events
    public static abstract class EmployeeFormEvent extends ComponentEvent<EmployeeDataForm> {
        private EmployeeModel employeeModel;
        protected EmployeeFormEvent(EmployeeDataForm source, EmployeeModel employeeModel) {
            super(source, false);
            this.employeeModel = employeeModel;
        }
        public EmployeeModel getEmployee() {
            return employeeModel;
        }
    }
    public static class SaveEvent extends EmployeeDataForm.EmployeeFormEvent {
        SaveEvent(EmployeeDataForm source, EmployeeModel employeeModel) {
            super(source, employeeModel);
        }
    }

    public static class CloseEvent extends EmployeeDataForm.EmployeeFormEvent {
        CloseEvent(EmployeeDataForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<EmployeeDataForm.SaveEvent> listener) {
        return addListener(EmployeeDataForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<EmployeeDataForm.CloseEvent> listener) {
        return addListener(EmployeeDataForm.CloseEvent.class, listener);
    }

}
