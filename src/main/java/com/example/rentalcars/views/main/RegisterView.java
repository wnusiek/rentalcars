package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.service.CompanyService;
import com.example.rentalcars.service.CustomerService;
import com.example.rentalcars.service.RoleService;
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
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final RoleService roleService;
    private TextField name;
    private EmailField email;
    private PasswordField password;

    Button registerButton = new Button("Zarejestruj");

    public RegisterView(UserService userService, CustomerService customerService, CompanyService companyService, RoleService roleService) {
        this.userService = userService;
        this.customerService = customerService;
        this.companyService = companyService;
        this.roleService = roleService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        registerButton.addClickListener(e -> register());
        add(
                new H1("Wypożyczalnia samochodów " + this.companyService.getCompanyName()),
                new H1("REJESTRACJA"),
                name = new TextField("Nazwa użytkownika"),
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
            RoleModel roleModel = roleService.getRoleByName("CUSTOMER");
            UserModel newUser = new UserModel();
            newUser.setName(firstName);
            newUser.setEmail(userEmail);
            newUser.setPassword(userPassword);
            newUser.setRole(roleModel);
            newUser.setState(true);

            if (userService.checkIfUserExists(newUser)) {
                Notification.show("Taki użytkownik już istnieje. Wybierz inną nazwę.").setPosition(Notification.Position.BOTTOM_CENTER);
            } else {
                userService.saveUser(newUser);
                CustomerModel newCustomer = new CustomerModel(1L,"","","","","","","",newUser);
                customerService.saveCustomer(newCustomer);

                Notification.show("Rejestracja zakończona sukcesem!").setPosition(Notification.Position.BOTTOM_CENTER);
                UI.getCurrent().navigate("login");
            }
        }
    }

    public boolean checkIfFieldEmpty() {
        return name.isEmpty() || email.isEmpty() || password.isEmpty();
    }
}

