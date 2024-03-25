package com.example.rentalcars.service;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.RoleRepository;
import com.example.rentalcars.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

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
//        given(userRepository.findAll()).willReturn(List.of(user,user1));
    }
}
