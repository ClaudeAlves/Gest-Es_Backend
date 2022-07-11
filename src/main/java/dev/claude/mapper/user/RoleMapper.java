package dev.claude.mapper.user;

import dev.claude.domain.user.Role;
import dev.claude.domain.user.EnumRole;
import dev.claude.dto.RoleDTO;
import dev.claude.mapper.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RoleMapper extends Mapper<Role, RoleDTO, RoleDTO> {

    public List<String> toStringRoles(Set<Role> roles) {
        List<String> stringRoles = new ArrayList<>();
        for (Role role : roles) {
            stringRoles.add(toStringRole(role));
        }
        return stringRoles;
    }
    private String toStringRole(Role role) {
        return role.getName().toString();
    }

    @Override
    public Role toModel(RoleDTO dtoObject) {
        return Role.builder()
                .id(dtoObject.getId())
                .name(EnumRole.getRole(dtoObject.getName().get()))
                .build();
    }

    @Override
    public RoleDTO toDto(Role modelObject) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(modelObject.getId());
        roleDTO.setName(JsonNullable.of(modelObject.getName().name()));
        return null;
    }

    @Override
    public Role toModelFromCreation(RoleDTO creationObject) {
        return null;
    }
}

