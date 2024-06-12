package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private Boolean state;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;

    public Boolean isActive(){
        return state;
    }
}
