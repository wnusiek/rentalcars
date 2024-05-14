package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.UserAdditionException;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.RoleRepository;
import com.example.rentalcars.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;
    private UserModel userModel;
    private RoleModel roleModel;

    @BeforeEach
    public void setup(){
        roleModel = new RoleModel(1L, "ADMIN");
        userModel = new UserModel(1L, "Bolek", "bolek123", "bolek@gmail.com", true, roleModel);
    }

    @Test
    public void testFindUserById_UserFound(){
        Long id = userModel.getId();
        given(userRepository.findById(id)).willReturn(Optional.of(userModel));

        UserModel savedUser = userService.findById(id);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isEqualTo(userModel);
    }

    @Test
    public void testFindUserById_ExceptionThrown(){
        Long id = userModel.getId();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList(){
        UserModel userModel1 = new UserModel(2L, "JimiHendrix", "jimi123", "jhendrix@gmail.com", true, roleModel);
        given(userRepository.findAll()).willReturn(List.of(userModel,userModel1));

        List<UserModel> userModelList = userService.getAllUsers();

        assertThat(userModelList).isNotNull();
        assertThat(userModelList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyUsersList_whenGetAllUsers_thenReturnEmptyUsersList(){
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        List<UserModel> userModelList = userService.getAllUsers();

        assertThat(userModelList).isEmpty();
    }

    @Test
    public void givenUserId_whenDeleteUser_thenNothing(){
        long userId = 1L;
        willDoNothing().given(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void givenUserObject_whenSaveUser_thenReturnUser(){
        given(userRepository.save(userModel)).willReturn(userModel);

        UserModel savedUser = userService.saveUser(userModel);

        assertThat(savedUser).isNotNull();

    }

    @Test
    public void givenUserModel_whenSaveUser_thenExceptionThrown(){
        when(userRepository.save(userModel)).thenThrow(new RuntimeException());
        assertThrows(UserAdditionException.class, () -> userService.saveUser(userModel));
    }

    @Test
    public void testCheckIfUserExists_UserExists(){
        given(userRepository.findByName("Bolek")).willReturn(Optional.of(userModel));

        boolean userExists = userService.checkIfUserExists(userModel);

        assertThat(userExists).isTrue();
    }

    @Test
    public void testCheckIfUserExists_UserDoesNotExists(){
        given(userRepository.findByName("Bolek")).willReturn(Optional.empty());

        boolean userExists = userService.checkIfUserExists(userModel);

        assertThat(userExists).isFalse();
    }
}
