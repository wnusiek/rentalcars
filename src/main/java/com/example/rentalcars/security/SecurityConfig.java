package com.example.rentalcars.security;

import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.UserRepository;
import com.example.rentalcars.service.UserService;
import com.example.rentalcars.views.main.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends VaadinWebSecurity {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/images/**");
        super.configure(web);
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return userName -> {
            // Pobierz użytkownika z bazy danych na podstawie nazwy użytkownika
            UserModel user = userService.findByName(userName);
            if (user == null) {
                throw new UsernameNotFoundException("Użytkownik o takim emailu nie został znaleziony.");
            }

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getName())
                    .password(user.getPassword())
                    .build();
        };
    }
}



