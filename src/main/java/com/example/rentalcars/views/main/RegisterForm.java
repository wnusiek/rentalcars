package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.UserModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.validation.constraints.Email;
import org.apache.catalina.User;


import java.awt.*;

public class RegisterForm extends FormLayout {

    private UserModel userModel;


    Binder<UserModel> binder = new BeanValidationBinder<>(UserModel.class);

    TextField name = new TextField("name");

    PasswordField passwordField = new PasswordField("password");

    EmailField emailField = new EmailField("email");

    Button registerButton = new Button("Register");


    public RegisterForm() {
        binder.bindInstanceFields(this);
        add(
                name,
                passwordField,
                emailField,
                registerButtonLayout()
        );
    }

    private Component registerButtonLayout() {
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(event -> validateAndSave());

        return new HorizontalLayout(registerButton);

    }

    private void validateAndSave() {
        try {
            binder.writeBean(userModel);
            fireEvent(new SaveEvent(this, userModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


public static abstract class ReqisterFormEvent extends ComponentEvent<RegisterForm> {

    private UserModel userModel;

    protected ReqisterFormEvent(RegisterForm source, UserModel userModel) {
        super(source, false);
        this.userModel = userModel;
    }
    public UserModel getUser() {
        return userModel;
    }
}

    public static class SaveEvent extends RegisterForm.ReqisterFormEvent {
        SaveEvent(RegisterForm source, UserModel userModel) {
            super(source, userModel);
        }
    }

    public Registration addSaveListener(ComponentEventListener<RegisterForm.SaveEvent> listener) {
        return addListener(RegisterForm.SaveEvent.class, listener);
    }
}


