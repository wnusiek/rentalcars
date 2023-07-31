package com.example.rentalcars.views.main;
import com.example.rentalcars.service.UserService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private UserService userService;

    private LoginForm login = new LoginForm();



    private RegisterForm registerForm = new RegisterForm();
    public LoginView(UserService userService) {
        this.userService = userService;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        configureForm();
        login.setAction("login");

        add(
                new H1("Wypożyczalnia Gruz-rental"),
                login,
                registerForm

        );


    }

    private void configureForm(){
        registerForm = new RegisterForm();
        registerForm.addSaveListener(this::saveUser);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
            login.setError(true);
        }
    }

    private void saveUser(RegisterForm.SaveEvent event){
        userService.saveUser(event.getUser());
    }
}
