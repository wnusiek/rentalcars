package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.RoleModel;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PermitAll
public class UserManagementForm extends FormLayout {
    Binder<UserModel> binder = new Binder<>(UserModel.class);
    TextField name = new TextField("Nazwa użytkownika");
    EmailField email = new EmailField("Email");
    ComboBox<RoleModel> role = new ComboBox<>("Rola użytkownika");
    ComboBox<Boolean> state = new ComboBox<>("Czy aktywny");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private UserModel userModel;

    public UserManagementForm(List<RoleModel> roleModelList) {
        state.setItems(List.of(true, false));
        role.setItems(roleModelList);
        role.setItemLabelGenerator(RoleModel::getName);
        binder.forField(state).bind(UserModel::getState, UserModel::setState);
        binder.bindInstanceFields(this);
        addClassName("user-form");
        add(
                name,
                email,
                state,
                role,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event ->  fireEvent(new UserManagementForm.DeleteEvent(this, userModel)));
        cancel.addClickListener(event -> fireEvent(new UserManagementForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(userModel);
            fireEvent(new UserManagementForm.SaveEvent(this, userModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setUser(UserModel userModel){
        this.userModel = userModel;
        binder.readBean(userModel);
    }


    //Events
    public static abstract class UserFormEvent extends ComponentEvent<UserManagementForm> {
        private UserModel userModel;
        protected UserFormEvent(UserManagementForm source, UserModel userModel) {
            super(source, false);
            this.userModel = userModel;
        }
        public UserModel getUser() {
            return userModel;
        }
    }
    public static class SaveEvent extends UserManagementForm.UserFormEvent {
        SaveEvent(UserManagementForm source, UserModel userModel) {
            super(source, userModel);
        }
    }
    public static class DeleteEvent extends UserManagementForm.UserFormEvent {
        DeleteEvent(UserManagementForm source, UserModel userModel) {
            super(source, userModel);
        }
    }
    public static class CloseEvent extends UserManagementForm.UserFormEvent {
        CloseEvent(UserManagementForm source) {
            super(source, null);
        }
    }
    public Registration addDeleteListener(ComponentEventListener<UserManagementForm.DeleteEvent> listener) {
        return addListener(UserManagementForm.DeleteEvent.class, listener);
    }
    public Registration addSaveListener(ComponentEventListener<UserManagementForm.SaveEvent> listener) {
        return addListener(UserManagementForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<UserManagementForm.CloseEvent> listener) {
        return addListener(UserManagementForm.CloseEvent.class, listener);
    }

}
