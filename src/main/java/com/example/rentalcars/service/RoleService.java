package com.example.rentalcars.service;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleModel getRoleByName(String name){
        var role = roleRepository.findByName(name);
        if (role.isPresent()){
            return role.get();
        } else {
            System.err.println("Nie ma takiej roli");
            return null;
        }

    }
}
