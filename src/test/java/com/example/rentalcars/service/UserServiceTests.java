package com.example.rentalcars.service;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.RoleRepository;
import com.example.rentalcars.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


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
        roleModel = new RoleModel(1l, "ADMIN");
        userModel = new UserModel(1L, "Bolek", "bolek123", "bolek@gmail.com", true, roleModel);
    }

    @Test
    public void givenUserId_whenFindById_thenReturnUser(){

        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(userModel));

        // when
        UserModel savedUser = userService.findById(userModel.getId());

        // then
        assertThat(savedUser).isNotNull();

    }

    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList(){

        // given
        UserModel userModel1 = new UserModel(2L, "JimiHendrix", "jimi123", "jhendrix@gmail.com", true, roleModel);
        given(userRepository.findAll()).willReturn(List.of(userModel,userModel1));

        // when
        List<UserModel> userModelList = userService.getAllUsers();

        // then
        assertThat(userModelList).isNotNull();
        assertThat(userModelList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyUsersList_whenGetAllUsers_thenReturnEmptyUsersList(){

        // given
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<UserModel> userModelList = userService.getAllUsers();

        // then
        assertThat(userModelList).isEmpty();
        assertThat(userModelList.size()).isEqualTo(0);
    }

    @Test
    public void givenUserId_whenDeleteUser_thenNothing(){

        // given
        long userId = 1L;
        willDoNothing().given(userRepository).deleteById(userId);

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void givenUserObject_whenAddUser_thenReturnUserObject(){

        // given
        given(userRepository.save(userModel))
                .willReturn(userModel);

        // when
        Boolean savedUser = userService.addUser(userModel);

        System.out.println(savedUser);

        // then
        assertThat(savedUser).isTrue();

    }
}
