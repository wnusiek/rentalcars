package com.example.rentalcars.views.main;

import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register")
@PageTitle("Rejestracja")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private final UserService userService;
    private final CompanyService companyService;

    private TextField name;
    private EmailField email;
    private PasswordField password;

    Button registerButton = new Button("Zarejestruj");

    // Inicjujemy pola i przycisk (tak jak wcześniej)


    public RegisterView(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        registerButton.addClickListener(e -> register());
 //       registerButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
        add(
                new H1("Wypożyczalnia samochodów " + this.companyService.getCompanyName()),
                new H1("REJESTRACJA"),
                name = new TextField("Imię"),
                email = new EmailField("E-mail"),
                password = new PasswordField("Hasło"),
                registerButton

        );
    }

    public void register() {

        if (checkIfFieldEmpty()) {
            Notification.show("Wszystkie pola są wymagane!").setPosition(Notification.Position.BOTTOM_CENTER);
        } else {
            String firstName = name.getValue();
            String userEmail = email.getValue();
            String userPassword = password.getValue();

            UserModel newUser = new UserModel();
            newUser.setName(firstName);
            newUser.setEmail(userEmail);
            newUser.setPassword(userPassword);

            if (userService.checkIfUserExists(newUser)) {
                Notification.show("User istnieje").setPosition(Notification.Position.BOTTOM_CENTER);
            } else {
                userService.saveUser(newUser);
                Notification.show("Rejestracja zakończona sukcesem!").setPosition(Notification.Position.BOTTOM_CENTER);
                UI.getCurrent().navigate("login");
            }
        }
    }

    public boolean checkIfFieldEmpty() {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return true;
        }
        return false;
    }
}

