package com.example.rentalcars.repository;

import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByName(String name);

    @Query("select u from UserModel u " +
            "where (:searchUser is null or lower(u.name) like lower(concat('%', :searchUser, '%'))" +
            "and (:searchRole is null or u.role = :searchRole))"
    )
    List<UserModel> search(
           @Param("searchUser") String filterText,
           @Param("searchRole") RoleModel role);
}
