package com.example.rentalcars.service;

import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public void addUser(UserModel userModel) {
        userRepository.save(userModel);
    }

    public List<UserModel> getUserList() {
        return userRepository.findAll();
    }

    public void updateUser(UserModel userToUpdate) {
        userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean saveUser(UserModel userModel) {
        var savedUser = userRepository.findByEmail(userModel.getEmail());
        if (savedUser.isPresent()) {
            Notification.show("User o emailu " + userModel.getEmail() + " już istnieje").setPosition(Notification.Position.BOTTOM_CENTER);
            return false;
        } else {
            userRepository.save(userModel);
            return true;
        }
    }

    public UserModel findUserByName(String name) {
        var user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            System.err.println("Nie ma takiego użytkownika");
            return null;
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

    public boolean isUserLogged(){
        if (!getNameOfLoggedUser().equals("anonymousUser") && !getNameOfLoggedUser().isEmpty()){
            return true;
        }
        return false;
    }

    public String getNameOfLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void syncEmail(CustomerModel customer){
        Optional<UserModel> user = userRepository.findByName(getNameOfLoggedUser());
        if (user.isPresent()){
            user.get().setEmail(customer.getEmail());
        }
    }

}

