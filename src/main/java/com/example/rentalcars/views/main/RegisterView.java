package com.example.rentalcars.views.main;

import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register")
@PageTitle("Rejestracja")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private final UserService userService;

    private TextField name;
    private EmailField email;
    private TextField password;

    Button registerButton = new Button("Zarejestruj");

    // Inicjujemy pola i przycisk (tak jak wcześniej)





    public RegisterView(UserService userService) {
        this.userService = userService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        registerButton.addClickListener(e -> register());
        add(
                new H1("REJESTRACJA"),
        name = new TextField("Imię"),
        email = new EmailField("E-mail"),
        password = new TextField("Hasło"),
                registerButton

        );
    }

    public void register() {
        String firstName = name.getValue();
        String userEmail = email.getValue();
        String userPassword = password.getValue();

        // Tworzymy nowy obiekt UserModel i ustawiamy odpowiednie pola
        UserModel newUser = new UserModel();
        newUser.setName(firstName);
        newUser.setEmail(userEmail);
        newUser.setPassword(userPassword);

        // Zapisujemy nowego użytkownika do bazy danych za pomocą UserService
        userService.saveUser(newUser);

        // Tutaj możesz dodać więcej logiki, np. obsługę błędów, powiadomienia itp.

        // W tym przykładzie używamy powiadomienia Vaadin do potwierdzenia rejestracji
        Notification.show("Rejestracja zakończona sukcesem!");
    }
}

