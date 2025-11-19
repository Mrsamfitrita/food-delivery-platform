// user-service/src/main/java/com/example/user/dto/UserDto.java

package com.example.user.dto;

import lombok.Data;
import java.util.Set;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private List<AddressDto> addresses;
    private Set<RoleDto> roles;
}