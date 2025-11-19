package com.example.restaurant.service;

import com.example.restaurant.dto.DishDto;
import com.example.restaurant.dto.RestaurantDto;
import com.example.restaurant.mapper.DishMapper;
import com.example.restaurant.mapper.RestaurantMapper;
import com.example.restaurant.model.Dish;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.DishRepository;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final RestaurantMapper restaurantMapper;
    private final DishMapper dishMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository, RestaurantMapper restaurantMapper, DishMapper dishMapper) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.restaurantMapper = restaurantMapper;
        this.dishMapper = dishMapper;
    }

    // --- Public Endpoints ---
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto getRestaurantMenu(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        return restaurantMapper.toDto(restaurant);
    }

    // --- Admin Endpoints ---
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.toModel(restaurantDto);
        restaurant.setDishes(null); // Блюда добавляются отдельно
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(saved);
    }

    public DishDto addDishToRestaurant(Long restaurantId, DishDto dishDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Dish dish = dishMapper.toModel(dishDto);
        dish.setRestaurant(restaurant);

        Dish savedDish = dishRepository.save(dish);
        return dishMapper.toDto(savedDish);
    }

    public void deleteRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant not found");
        }
        restaurantRepository.deleteById(restaurantId);
    }
}