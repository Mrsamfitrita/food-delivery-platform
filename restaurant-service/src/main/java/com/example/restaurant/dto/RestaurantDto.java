package com.example.restaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestaurantDto {
    private Long id;
    private String name;
    private String cuisineType;
    private String location;
    private List<DishDto> dishes; //
}