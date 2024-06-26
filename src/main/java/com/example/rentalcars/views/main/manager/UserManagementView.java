package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.RoleRepository;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@Route(value = "userManagementView", layout = MainLayout.class)
@PageTitle("Użytkownicy")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
public class UserManagementView extends VerticalLayout {
    private final UserService userService;
    Grid<UserModel> grid = new Grid<>(UserModel.class, false);
    UserManagementForm form;
    List<RoleModel> roleModelList;
    TextField filterText = new TextField("User");
    ComboBox<RoleModel> roleType = new ComboBox<>("Rola");

    public UserManagementView(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleModelList = roleRepository.findAll();
        addClassName("user-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
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
        grid.setItems(userService.findWithFilter(filterText.getValue(), roleType.getValue()));
    }

    private Component getToolbar(){
        filterText.setPlaceholder("Wpisz nazwę usera...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateUserList());

        roleType.setPlaceholder("Wybierz rolę...");
        roleType.setClearButtonVisible(true);
        roleType.setItems(roleModelList);
        roleType.setItemLabelGenerator(RoleModel::getName);
        roleType.addValueChangeListener(e -> updateUserList());

        Button addUserButton = new Button("Dodaj użytkownika");
        addUserButton.addClickListener(e->addUser());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, roleType, addUserButton);
        toolbar.addClassName("toolbar");
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return toolbar;
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
        form = new UserManagementForm(roleModelList);
        form.setWidth("25em");

        form.addSaveListener(this::saveUser);
        form.addCloseListener(event -> closeEditor());
    }

    private void saveUser(UserManagementForm.SaveEvent event){
        userService.saveUser(event.getUser());
        updateUserList();
        closeEditor();
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new UserModel());
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
        grid.addColumn("name").setHeader("Nazwa użytkownika");
        grid.addColumn("email").setHeader("Email");
        grid.addColumn("state").setHeader("Czy aktywny");
        grid.addColumn("role.name").setHeader("Rola użytkownika");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    }

}
