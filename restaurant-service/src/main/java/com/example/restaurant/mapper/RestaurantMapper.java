package com.example.restaurant.mapper;

import com.example.restaurant.dto.RestaurantDto;
import com.example.restaurant.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DishMapper.class)
public interface RestaurantMapper {
    RestaurantDto toDto(Restaurant restaurant);
    @Mapping(target = "id", ignore = true)
    Restaurant toModel(RestaurantDto restaurantDto);
}