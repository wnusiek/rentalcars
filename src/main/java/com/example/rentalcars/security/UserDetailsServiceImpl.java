package com.example.rentalcars.security;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findAll().stream().filter(user -> user.getName().equals(username)).findFirst().orElse(null);
        if (userModel == null) {
            throw new UsernameNotFoundException("User nie znaleziony");
        }
        return new org.springframework.security.core.userdetails.User(
                userModel.getName(),
                userModel.getPassword(),
                userModel.isEnabled(),
                true, true, true,
                getAuthorities(userModel.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(RoleModel role){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }

}
