// user-service/src/main/java/com/example/user/mapper/UserMapper.java

package com.example.user.mapper;


import com.example.user.dto.UserDto;
import com.example.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {AddressMapper.class, RoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "roles", source = "roles")
    User toModel(UserDto userDto);

    UserDto toDto(User user);
}