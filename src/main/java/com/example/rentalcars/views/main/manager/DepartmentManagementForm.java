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
public class DepartmentManagementForm extends FormLayout {
    Binder<DepartmentModel> departmentBinder = new BeanValidationBinder<>(DepartmentModel.class);
    TextField city = new TextField("Oddział");
    TextField address = new TextField("Adres oddziału");
    TextField phone = new TextField("Telefon");
    Button save = new Button("Zapisz oddział");
    Button delete = new Button("Usuń oddział");
    Button cancel = new Button("Anuluj");
    private DepartmentModel departmentModel;

    public DepartmentManagementForm() {
        departmentBinder.bindInstanceFields(this);
        addClassName("department-form");
        add(
                city,
                address,
                phone,
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
        cancel.addClickListener(event -> fireEvent(new DepartmentManagementForm.CloseEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(city.isEmpty() || address.isEmpty() || phone.isEmpty()){
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
    public static abstract class DepartmentFormEvent extends ComponentEvent<DepartmentManagementForm> {
        private DepartmentModel departmentModel;

        protected DepartmentFormEvent(DepartmentManagementForm source, DepartmentModel departmentModel) {
            super(source, false);
            this.departmentModel = departmentModel;
        }

        public DepartmentModel getDepartment() {
            return departmentModel;
        }
    }
    public static class SaveEvent extends DepartmentManagementForm.DepartmentFormEvent {
        SaveEvent(DepartmentManagementForm source, DepartmentModel departmentModel) {
            super(source, departmentModel);
        }
    }
        public static class DeleteEvent extends DepartmentManagementForm.DepartmentFormEvent{
        DeleteEvent(DepartmentManagementForm source, DepartmentModel departmentModel){
            super(source, departmentModel);
        }
    }
    public static class CloseEvent extends DepartmentManagementForm.DepartmentFormEvent {
        CloseEvent(DepartmentManagementForm source) {
            super(source, null);
        }
    }

        public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener){
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<DepartmentManagementForm.SaveEvent> listener) {
        return addListener(DepartmentManagementForm.SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<DepartmentManagementForm.CloseEvent> listener) {
        return addListener(DepartmentManagementForm.CloseEvent.class, listener);
    }







}
