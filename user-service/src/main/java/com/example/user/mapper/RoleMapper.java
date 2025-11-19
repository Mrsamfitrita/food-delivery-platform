package com.example.user.mapper;

import com.example.user.dto.RoleDto;
import com.example.user.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id", ignore = true)
    Role toModel(RoleDto roleDto);

    RoleDto toDto(Role role);
}