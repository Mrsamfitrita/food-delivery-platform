// user-service/src/main/java/com/example/user/dto/UserRegistrationDto.java

package com.example.user.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class UserRegistrationDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String fullName;

    // Сюда можно добавить начальный адрес, если это требуется при регистрации
    private AddressDto initialAddress;
}