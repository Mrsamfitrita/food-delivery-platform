// user-service/src/main/java/com/example/user/dto/AddressDto.java

package com.example.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String zip;
    private String state;
    private String country;
}