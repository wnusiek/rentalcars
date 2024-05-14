package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.UserAdditionException;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<UserModel> userModel = userRepository.findById(id);
        return userModel.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o id = " + id));
    }

    public UserModel saveUser(UserModel userModel) {
        try {
            UserModel savedUser = userRepository.save(userModel);
            System.out.println("Użytkownik został dodany pomyślnie");
            return savedUser;
        } catch (Exception e){
            throw new UserAdditionException("Błąd podczas dodawania użytkownika.", e);
        }
    }

    public boolean checkIfUserExists(UserModel userModel) {
        return userRepository.findByName(userModel.getName()).isPresent();
    }

    public boolean isUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
    }

    public String getNameOfLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}

