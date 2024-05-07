package com.example.rentalcars.service;

import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean addUser(UserModel userModel) {
        if (userModel == null) {
            return false;
        } else {
            userRepository.save(userModel);
            return true;
        }
    }

    public boolean checkIfUserExists(UserModel userModel) {
        var name = userRepository.findByName(userModel.getName());
        var email = userRepository.findByEmail(userModel.getEmail());
        if (name.isPresent()) {
            Notification.show("Podaj inną nazwę użytkownika niż " + name.get().getName()).setPosition(Notification.Position.BOTTOM_CENTER);
            return true;
        }
        if (email.isPresent()) {
            Notification.show("Podaj inny email niż " + email.get().getEmail()).setPosition(Notification.Position.BOTTOM_CENTER);
            return true;
        }
        return false;
    }

    public boolean isUserLogged() {
        return !getNameOfLoggedUser().equals("anonymousUser") && !getNameOfLoggedUser().isEmpty();
    }

    public String getNameOfLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}

