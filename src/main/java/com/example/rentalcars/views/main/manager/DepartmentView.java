package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route(value = "departments", layout = MainLayout.class)
@PageTitle("Lista oddziałów")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class DepartmentView extends VerticalLayout {

    private final DepartmentService departmentService;
    Grid<DepartmentModel> grid = new Grid<>(DepartmentModel.class, false);
    DepartmentForm form = new DepartmentForm();

    public DepartmentView(DepartmentService departmentService) {
        this.departmentService = departmentService;
        addClassName("departments-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        updateDepartmentList();
        closeEditor();
    }

    private void closeEditor(){
        form.setDepartment(null);
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

    private void configureForm(){
        form = new DepartmentForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveDepartment);
        form.addDeleteListener(this::deleteDepartment);
        form.addCloseListener(closeEvent -> closeEditor());
    }

    private void deleteDepartment(DepartmentForm.DeleteEvent event){
        departmentService.deleteDepartment(event.getDepartment());
        updateDepartmentList();
        closeEditor();
    }

    private void saveDepartment(DepartmentForm.SaveEvent event){
        departmentService.postAddDepartment(event.getDepartment());
        updateDepartmentList();
        closeEditor();
    }

    private Component getToolbar(){
        Button addDepartmentButton = new Button("Dodaj oddział");
        addDepartmentButton.addClickListener(e-> addDepartment());

        HorizontalLayout toolbar = new HorizontalLayout(addDepartmentButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addDepartment(){
        grid.asSingleSelect().clear();
        editDepartment(new DepartmentModel());
    }

    private void updateDepartmentList(){
        grid.setItems(departmentService.getDepartmentList1());
    }

    private void configureGrid(){
        grid.addClassNames("departments-grid");
        grid.setSizeFull();
        grid.addColumn("city").setHeader("Miasto");
        grid.addColumn("address").setHeader("Adres oddziału");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editDepartment(event.getValue()));
    }

    private void editDepartment(DepartmentModel departmentModel){
        if (departmentModel == null){
            closeEditor();
        } else {
            form.setDepartment(departmentModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
