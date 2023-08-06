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
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;
import jakarta.validation.constraints.Email;
import org.apache.catalina.User;


import java.awt.*;

public class RegisterForm extends FormLayout {

    Binder<UserModel> binder = new BeanValidationBinder<>(UserModel.class);

    TextField name = new TextField("name");

    PasswordField password= new PasswordField("password");

    EmailField email = new EmailField("email");

    Button save = new Button("Register");

    private UserModel userModel;


    public RegisterForm() {
        binder.bindInstanceFields(this);
        binder.forField(email).withValidator(new EmailValidator(
                "Niepoprawny email")).bind(UserModel::getEmail, UserModel::setEmail);
        add(
                name,
                password,
                email,
                registerButtonLayout()
        );
    }

   public void setUser(UserModel userModel){
        this.userModel = userModel;
        binder.readBean(userModel);
   }

    private Component registerButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> validateAndSave());

        return new HorizontalLayout(save);

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

    public static class SaveEvent extends ReqisterFormEvent {
        SaveEvent(RegisterForm source, UserModel userModel) {
            super(source, userModel);
        }
    }

    public Registration addSaveListener(ComponentEventListener<RegisterForm.SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
}


