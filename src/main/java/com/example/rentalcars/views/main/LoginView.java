package com.example.rentalcars.views.main;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.service.UserService;
import com.vaadin.flow.component.button.Button;
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

    private final UserService userService;
    private final CompanyService companyService;

    LoginForm login = new LoginForm();

    Button registerButton = new Button("Zarejestruj się");




    RegisterForm registerForm = new RegisterForm();
    public LoginView(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        configureForm();
        login.setAction("login");

        registerButton.addClickListener(e-> getUI().ifPresent(ui -> ui.navigate("register")));

        add(
                new H1("Wypożyczalnia samochodów " + companyService.getCompanyName()),
                login,
                new H1("Nie masz jeszcze konta? Zarejestruj się!"),
                registerButton


        );


    }

    private void configureForm(){
        setAlignItems(Alignment.CENTER);
        registerForm = new RegisterForm();
        registerForm.setWidth("25em");

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
