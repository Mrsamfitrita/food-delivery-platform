package com.example.restaurant.controller;

import com.example.restaurant.dto.DishDto;
import com.example.restaurant.dto.RestaurantDto;
import com.example.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Public: List all restaurants
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    // Public: Get specific restaurant menu
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantMenu(id));
    }

    // Admin: Create restaurant [cite: 13, 8]
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        RestaurantDto created = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Admin: Delete restaurant
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // Admin: Add dish to menu
    @PostMapping("/{restaurantId}/dishes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DishDto> addDishToRestaurant(
            @PathVariable Long restaurantId,
            @Valid @RequestBody DishDto dishDto) {
        DishDto createdDish = restaurantService.addDishToRestaurant(restaurantId, dishDto);
        return new ResponseEntity<>(createdDish, HttpStatus.CREATED);
    }
}