package com.example.rentalcars.service;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;
    private RoleModel roleModel;

    @BeforeEach
    public void setup(){
        roleModel = new RoleModel(1l, "CUSTOMER");
    }

    @Test
    public void givenRoleName_whenGetRoleByName_thenReturnRoleModel(){

        // given
        String name = "CUSTOMER";
        given(roleRepository.findByName(name)).willReturn(Optional.ofNullable(roleModel));

        // when
        RoleModel savedRole = roleService.getRoleByName(name);

        // then
        assertThat(savedRole).isNotNull();
    }

    @Test
    public void givenRoleName_whenGetRoleByName_thenReturnNull(){

        // given
        String name = "STANDARD";
        given(roleRepository.findByName(name)).willReturn(Optional.ofNullable(null));

        // when
        RoleModel savedRole = roleService.getRoleByName(name);

        //then
        assertThat(savedRole).isNull();
    }

    @Test
    public void givenRoleEmptyName_whenGetRoleByName_thenReturnNull(){

        // given
        String name = "";
        given(roleRepository.findByName(name)).willReturn(Optional.ofNullable(null));

        // when
        RoleModel savedRole = roleService.getRoleByName(name);

        //then
        assertThat(savedRole).isNull();
    }
}
