package com.example.rentalcars.DTO;

import com.example.rentalcars.model.CarModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DepartmentDTO {

    private Long id;

    private String city;

    private List<CarModel> cars;
}
