package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.CompanyModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
public class CompanyManagementForm extends VerticalLayout {
    Binder<CompanyModel> binder = new BeanValidationBinder<>(CompanyModel.class);
    TextField companyName = new TextField("Company name");
    TextField companyDomainURL = new TextField("Company domain URL");
    TextField companyAddress = new TextField("Company address");
    TextField companyOwner = new TextField("Company owner");
    TextField companyLogotype = new TextField("Company Logotype");

    Button save = new Button("Save");
    private CompanyModel companyModel;

    public CompanyManagementForm(){
        binder.bindInstanceFields(this);
        addClassName("company-form");
        add(
                companyName,
                companyDomainURL,
                companyAddress,
                companyOwner,
                companyLogotype,
                createButtonLayout()
        );
    }
    public void setCompany(CompanyModel companyModel){
        this.companyModel = companyModel;
        binder.readBean(companyModel);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> validateAndSave());
        return new HorizontalLayout(save);
    }
    private void validateAndSave() {
        try {
            binder.writeBean(companyModel);
            fireEvent(new SaveEvent(this, companyModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class CompanyFormEvent extends ComponentEvent<CompanyManagementForm> {
        private CompanyModel companyModel;
        protected CompanyFormEvent(CompanyManagementForm source, CompanyModel companyModel) {
            super(source, false);
            this.companyModel = companyModel;
        }
        public CompanyModel getCompany() {
            return companyModel;
        }
    }
    public static class SaveEvent extends CompanyManagementForm.CompanyFormEvent {
        SaveEvent(CompanyManagementForm source, CompanyModel companyModel) {
            super(source, companyModel);
        }
    }

    public static class CloseEvent extends CompanyManagementForm.CompanyFormEvent {
        CloseEvent(CompanyManagementForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<CompanyManagementForm.SaveEvent> listener) {
        return addListener(CompanyManagementForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CompanyManagementForm.CloseEvent> listener) {
        return addListener(CompanyManagementForm.CloseEvent.class, listener);
    }
}
