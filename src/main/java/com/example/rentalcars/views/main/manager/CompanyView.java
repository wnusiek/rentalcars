package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.CompanyModel;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "configuration", layout = MainLayout.class)
@PageTitle("Konfiguracja")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class CompanyView extends VerticalLayout {

    private final CompanyService companyService;
    Grid<CompanyModel> grid = new Grid<>(CompanyModel.class);
    CompanyForm form = new CompanyForm();

    public CompanyView(CompanyService companyService) {
        this.companyService = companyService;
        addClassName("company-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getContent());
        updateCompanyInfo();
        closeEditor();

    }
    private void closeEditor() {
        form.setCompany(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new CompanyForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveCompany);
        form.addCloseListener(event -> closeEditor());
    }

    private void saveCompany(CompanyForm.SaveEvent event){
        companyService.updateCompany(event.getCompany());
        updateCompanyInfo();
        closeEditor();
    }
    private void updateCompanyInfo() {
        grid.setItems(companyService.getCompanyModelList());
    }
    private void configureGrid() {
        grid.addClassNames("company-grid");
        grid.setSizeFull();
        grid.setColumns("companyName", "companyDomainURL", "companyAddress", "companyOwner", "companyLogotype");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editCompany(event.getValue()));
    }
    private void editCompany(CompanyModel companyModel) {
        if(companyModel == null){
            closeEditor();
        }else {
            form.setCompany(companyModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

//    private Component getToolbar(){
//        Button addEmployeeButton = new Button("Ustaw info ");
//        addEmployeeButton.addClickListener(event -> editCompany(event.getValue()));
//
//        HorizontalLayout toolbar = new HorizontalLayout(addEmployeeButton);
//        toolbar.addClassName("toolbar");
//        return toolbar;
//    }

}
