package com.example.rentalcars.service;

import com.example.rentalcars.model.UserModel;
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

    private PasswordEncoder passwordEncoder;

    public void addUser(UserModel userModel){
        userRepository.save(userModel);
    }

    public List<UserModel> getUserList(){
        return userRepository.findAll();
    }

    public void updateUser(UserModel userToUpdate){
        userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserModel findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public UserModel findByName(String name){
        for(UserModel user: getUserList()){
            if(user.getName().equals(name)){
                return user;
            }
        }return null;
    }

    public void saveUser(UserModel userModel) {
        if (userModel.getName().equals(findByName(userModel.getName()))) {
            System.err.println("User exists");
        } else {
            UserModel user = new UserModel();
            user.setName(userModel.getName());
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));

            userRepository.save(user);
        }
    }

}
