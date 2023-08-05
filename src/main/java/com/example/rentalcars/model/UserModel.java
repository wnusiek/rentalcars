package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private Boolean active;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;


    public Boolean isEnabled(){
        return active;
//        if (active){
//            return true;
//        }
//        return false;
    }
}
