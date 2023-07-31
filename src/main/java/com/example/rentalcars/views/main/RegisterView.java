package com.example.rentalcars.views.main;

import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.apache.catalina.User;

public class RegisterView extends HorizontalLayout {

    private final UserService userService;

    Grid<UserModel> grid = new Grid<>(UserModel.class);

    RegisterForm registerForm = new RegisterForm();

    public RegisterView(UserService userService){
        this.userService = userService;
        addClassName("register-view");
        setSizeFull();
        add(
                registerForm
        );
    }
}
