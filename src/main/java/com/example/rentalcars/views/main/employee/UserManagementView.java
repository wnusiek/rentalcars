package com.example.rentalcars.views.main.employee;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.RoleRepository;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.Collections;
import java.util.List;

@Route(value = "usermanagement", layout = MainLayout.class)
@PageTitle("Użytkownicy")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class UserManagementView extends VerticalLayout {
    private final UserService userService;
    Grid<UserModel> grid = new Grid<>(UserModel.class, false);
    UserForm form;
    List<RoleModel> roleModelList;

    public UserManagementView(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleModelList = roleRepository.findAll();
        addClassName("user-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getContent()
        );
        updateUserList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateUserList() {
        grid.setItems(userService.getAllUsers());
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
        form = new UserForm(roleModelList);
        form.setWidth("25em");

        form.addSaveListener(this::saveUser);
        form.addCloseListener(event -> closeEditor());
    }

    private void saveUser(UserForm.SaveEvent event){
        userService.addUser(event.getUser());
        updateUserList();
        closeEditor();
    }

    private void editUser(UserModel userModel){
        if (userModel == null){
            closeEditor();
        } else {
            form.setUser(userModel);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureGrid() {
        grid.addClassNames("users-grid");
        grid.setSizeFull();
        grid.addColumn("name").setHeader("Username");
        grid.addColumn("email").setHeader("Email");
        grid.addColumn("active").setHeader("Czy aktywny");
        grid.addColumn("role.name").setHeader("Rola");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    }

}
