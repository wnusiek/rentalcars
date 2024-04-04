package com.example.rentalcars.repository;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {
    @Query("select c from CarModel c " +
            "where lower(c.mark) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.model) like lower(concat('%', :searchTerm, '%'))")
    List<CarModel> search(@Param("searchTerm") String searchTerm);
}
