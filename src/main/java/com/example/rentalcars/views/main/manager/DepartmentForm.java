package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.DepartmentModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class DepartmentForm extends FormLayout {
    Binder<DepartmentModel> departmentBinder = new BeanValidationBinder<>(DepartmentModel.class);
    TextField city = new TextField("Miasto");
    TextField address = new TextField("Adres oddziału");
    Button save = new Button("Zapisz oddział");
    Button delete = new Button("Usuń oddział");
    Button cancel = new Button("Anuluj");
    private DepartmentModel departmentModel;

    public DepartmentForm() {
        departmentBinder.bindInstanceFields(this);
        addClassName("department-form");
        add(
                city,
                address,
                createButtonLayout()
        );
    }

    public void setDepartment(DepartmentModel departmentModel){
        this.departmentModel = departmentModel;
        departmentBinder.readBean(departmentModel);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, departmentModel)));
        cancel.addClickListener(event -> fireEvent(new DepartmentForm.CloseEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(city.isEmpty() || address.isEmpty()){
            Notification.show("Wszystkie pola są wymagane");
            return;
        }
        try{
            departmentBinder.writeBean(departmentModel);
            fireEvent(new SaveEvent(this, departmentModel));
        } catch (ValidationException e){
            e.printStackTrace();
        }
    }

    //EVENTS
    public static abstract class DepartmentFormEvent extends ComponentEvent<DepartmentForm> {
        private DepartmentModel departmentModel;

        protected DepartmentFormEvent(DepartmentForm source, DepartmentModel departmentModel) {
            super(source, false);
            this.departmentModel = departmentModel;
        }

        public DepartmentModel getDepartment() {
            return departmentModel;
        }
    }
    public static class SaveEvent extends DepartmentForm.DepartmentFormEvent {
        SaveEvent(DepartmentForm source, DepartmentModel departmentModel) {
            super(source, departmentModel);
        }
    }
        public static class DeleteEvent extends DepartmentForm.DepartmentFormEvent{
        DeleteEvent(DepartmentForm source, DepartmentModel departmentModel){
            super(source, departmentModel);
        }
    }
    public static class CloseEvent extends DepartmentForm.DepartmentFormEvent {
        CloseEvent(DepartmentForm source) {
            super(source, null);
        }
    }

        public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener){
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<DepartmentForm.SaveEvent> listener) {
        return addListener(DepartmentForm.SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<DepartmentForm.CloseEvent> listener) {
        return addListener(DepartmentForm.CloseEvent.class, listener);
    }







}
