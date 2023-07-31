package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.EmployeeModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class CustomerForm extends FormLayout {
    Binder<CustomerModel> binder = new BeanValidationBinder<>(CustomerModel.class);
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private CustomerModel customerModel;


    public CustomerForm() {
        binder.bindInstanceFields(this);
        add(
                firstName,
                lastName,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event ->  fireEvent(new CustomerForm.DeleteEvent(this, customerModel)));
        cancel.addClickListener(event -> fireEvent(new CustomerForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(customerModel);
            fireEvent(new SaveEvent(this, customerModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setCustomer(CustomerModel customerModel){
        this.customerModel = customerModel;
        binder.readBean(customerModel);
    }

    //Events
    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private CustomerModel customerModel;
        protected CustomerFormEvent(CustomerForm source, CustomerModel customerModel) {
            super(source, false);
            this.customerModel = customerModel;
        }
        public CustomerModel getCustomer() {
            return customerModel;
        }
    }
    public static class SaveEvent extends CustomerForm.CustomerFormEvent {
        SaveEvent(CustomerForm source, CustomerModel customerModel) {
            super(source, customerModel);
        }
    }
    public static class DeleteEvent extends CustomerForm.CustomerFormEvent {
        DeleteEvent(CustomerForm source, CustomerModel customerModel) {
            super(source, customerModel);
        }
    }
    public static class CloseEvent extends CustomerForm.CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }
    public Registration addDeleteListener(ComponentEventListener<CustomerForm.DeleteEvent> listener) {
        return addListener(CustomerForm.DeleteEvent.class, listener);
    }
    public Registration addSaveListener(ComponentEventListener<CustomerForm.SaveEvent> listener) {
        return addListener(CustomerForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CustomerForm.CloseEvent> listener) {
        return addListener(CustomerForm.CloseEvent.class, listener);
    }






















}
