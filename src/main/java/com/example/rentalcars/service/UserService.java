package com.example.rentalcars.service;

import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.RoleRepository;
import com.example.rentalcars.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
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

    public boolean findByName(String name) {
        for (UserModel user : getUserList()) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public UserModel findByNameModel(String name) {
        for (UserModel user : getUserList()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void saveUser(UserModel userModel) {

            UserModel user = new UserModel();
            user.setName(userModel.getName());
            user.setEmail(userModel.getEmail());
            user.setPassword(userModel.getPassword());
            user.setRole(roleRepository.findById(1l).orElse(null));
            user.setActive(true);
            userRepository.save(user);

    }

    public boolean checkIfUserExists(UserModel userModel) {
        if (findByName(userModel.getName())) {
            return true;
        }
        return false;
    }

}

