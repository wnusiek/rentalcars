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


import java.util.List;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;


    @InjectMocks
    private UserService userService;

    private UserModel userModel;

    @BeforeEach
    public void setup(){
        var role1 = roleRepository.save(new RoleModel(1l, "ADMIN"));
        userModel = new UserModel(1L, "Bolek", "bolek123", "bolek@gmail.com", true, role1);
    }

    @Test
    public void givenUserList_whenGetAllUsers_thenReturnUsersList(){

        // given
        var role1 = roleRepository.save(new RoleModel(1l, "ADMIN"));
        UserModel userModel1 = new UserModel(2L, "JimiHendrix", "jimi123", "jhendrix@gmail.com", true, role1);
        given(userRepository.findAll()).willReturn(List.of(userModel,userModel1));

        // when
        List<UserModel> userModelList = userService.getUserList();

        // then
        assertThat(userModelList).isNotNull();
        assertThat(userModelList.size()).isEqualTo(2);

    }
}
