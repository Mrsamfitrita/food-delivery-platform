package com.example.user.controller;

import com.example.user.dto.UserDto;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // Конструктор будет использован Spring (убедись, что UserService — @Service и @Autowired где нужно)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*Получение профиля текущего аутентифицированного пользователя.
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")  // Правильный SpEL-вызов
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDto userDto = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(userDto);
    }

    /*Обновление профиля текущего аутентифицированного пользователя.
     */
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserDto userDto) {

        // Находим текущего пользователя по email из токена
        UserDto currentUserDto = userService.findByEmail(userDetails.getUsername());

        // Обновляем, передавая ID из БД (защита от подмены)
        UserDto updatedUser = userService.updateProfile(currentUserDto.getId(), userDto);
        return ResponseEntity.ok(updatedUser);
    }
}