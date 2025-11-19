package com.example.restaurant.mapper;

import com.example.restaurant.dto.DishDto;
import com.example.restaurant.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {
    DishDto toDto(Dish dish);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Dish toModel(DishDto dishDto);
}