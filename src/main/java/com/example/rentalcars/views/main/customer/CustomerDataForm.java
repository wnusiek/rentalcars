package com.example.rentalcars.views.main.customer;

import com.example.rentalcars.model.CustomerModel;
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
public class CustomerDataForm extends FormLayout {
    Binder<CustomerModel> binder = new BeanValidationBinder<>(CustomerModel.class);
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField phoneNumber = new TextField("Phone number");
    TextField driverLicenceNumber = new TextField("Driver licence number");
    EmailField email = new EmailField("Email");
    TextField pesel = new TextField("Pesel");
    TextField city = new TextField("City");
    TextField zipCode = new TextField("Zip code");
    Button save = new Button("Save");
    Button cancel = new Button("Cancel");
    private CustomerModel customerModel;

    public CustomerDataForm() {
        binder.bindInstanceFields(this);
        binder.forField(email).withValidator(new EmailValidator("Niepoprawny email"))
                .bind(customer -> customer.getUser().getEmail(), (customer, email) -> customer.getUser().setEmail(email));
        add(
                firstName,
                lastName,
                phoneNumber,
                driverLicenceNumber,
                email,
                pesel,
                city,
                zipCode,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> validateAndSave());
        save.addClickShortcut(Key.ENTER);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickListener(event -> fireEvent(new CustomerDataForm.CloseEvent(this)));
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
            binder.writeBean(customerModel);
            fireEvent(new SaveEvent(this, customerModel));
            Notification.show("Zapisano").setPosition(Notification.Position.MIDDLE);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setCustomer(CustomerModel customerModel){
        this.customerModel = customerModel;
        binder.readBean(customerModel);
    }

    //Events
    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerDataForm> {
        private CustomerModel customerModel;
        protected CustomerFormEvent(CustomerDataForm source, CustomerModel customerModel) {
            super(source, false);
            this.customerModel = customerModel;
        }
        public CustomerModel getCustomer() {
            return customerModel;
        }
    }
    public static class SaveEvent extends CustomerDataForm.CustomerFormEvent {
        SaveEvent(CustomerDataForm source, CustomerModel customerModel) {
            super(source, customerModel);
        }
    }

    public static class CloseEvent extends CustomerDataForm.CustomerFormEvent {
        CloseEvent(CustomerDataForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<CustomerDataForm.SaveEvent> listener) {
        return addListener(CustomerDataForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CustomerDataForm.CloseEvent> listener) {
        return addListener(CustomerDataForm.CloseEvent.class, listener);
    }

}
