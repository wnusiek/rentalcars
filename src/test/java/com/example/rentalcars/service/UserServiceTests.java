package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.UserAdditionException;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private UserModel userModel;
    private RoleModel roleModel;
    private String username;
    private Long userId;
    @BeforeEach
    public void setup(){
        roleModel = new RoleModel(1L, "ADMIN");
        userModel = new UserModel(1L, "Bolek", "bolek123", "bolek@gmail.com", true, roleModel);
        username = "user1111";
        userId = 1L;
        userService = new UserService(userRepository);
    }

    @Test
    public void testFindUserById_ReturnUser(){
        given(userRepository.findById(userId)).willReturn(Optional.of(userModel));
        var savedUser = userService.findById(userId);
        assertThat(savedUser).isEqualTo(userModel);
    }

    @Test
    public void testFindUserById_ExceptionThrown(){
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    public void testGetAllUsers_ReturnList(){
        given(userRepository.findAll()).willReturn(List.of(userModel, new UserModel()));
        var savedUserList = userService.getAllUsers();
        assertThat(savedUserList.size()).isEqualTo(2);
    }

    @Test
    public void testGetAllUsers_ReturnEmptyList(){
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        var savedUserList = userService.getAllUsers();
        assertThat(savedUserList).isEmpty();
    }

    @Test
    public void testDeleteUser(){
        willDoNothing().given(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testSaveUser_ReturnUser(){
        given(userRepository.save(userModel)).willReturn(userModel);
        var savedUser = userService.saveUser(userModel);
        assertThat(savedUser).isNotNull();
    }

    @Test
    public void testSaveUser_ExceptionThrown(){
        given(userRepository.save(userModel)).willThrow(new RuntimeException());
        assertThrows(UserAdditionException.class, () -> userService.saveUser(userModel));
    }

    @Test
    public void testCheckIfUserExists_UserExists(){
        given(userRepository.findByName("Bolek")).willReturn(Optional.of(userModel));
        var userExists = userService.checkIfUserExists(userModel);
        assertThat(userExists).isTrue();
    }

    @Test
    public void testCheckIfUserExists_UserDoesNotExist(){
        given(userRepository.findByName("Bolek")).willReturn(Optional.empty());
        var userExists = userService.checkIfUserExists(userModel);
        assertThat(userExists).isFalse();
    }

    @Test
    public void testGetNameOfLoggedUser_ReturnUsername(){
        Authentication authentication = new TestingAuthenticationToken(username, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var loggedUsername = userService.getNameOfLoggedUser();
        assertThat(username).isEqualTo(loggedUsername);
    }

    @Test
    public void testIsUserLogged_UserAuthenticated() {
        Authentication authentication = new TestingAuthenticationToken(username, "password", "ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var result = userService.isUserLogged();
        assertThat(result).isTrue();
    }

    @Test
    public void testIsUserLogged_UserNotAuthenticated() {
        Authentication authentication = new AnonymousAuthenticationToken("key", "anonymousUser",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var result = userService.isUserLogged();
        assertThat(result).isFalse();
    }
}
