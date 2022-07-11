package dev.claude.mapper.user;

import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.Role;
import dev.claude.dto.LoginSuccessDTO;
import dev.claude.dto.RegisterDTO;
import dev.claude.dto.RoleDTO;
import dev.claude.dto.UserDTO;
import dev.claude.mapper.Mapper;
import dev.claude.mapper.user.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class UserMapper extends Mapper<AppUser, UserDTO, UserDTO> {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public AppUser toModel(UserDTO dtoObject) {
        List<Role> roleList = new LinkedList<>();
        dtoObject.getRoles().forEach(roleDTO -> {
            roleList.add(roleMapper.toModel(roleDTO));
        });

        return AppUser.builder()
                .idUser(dtoObject.getIdUser())
                .username(dtoObject.getUsername())
                .firstName(dtoObject.getFirstname())
                .lastName(dtoObject.getLastname())
                .email(dtoObject.getEmail())
                .password(dtoObject.getPassword())
                .roles(roleList)
                .build();
    }

    @Override
    public UserDTO toDto(AppUser modelObject) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser((int)modelObject.getIdUser());
        userDTO.setUsername(modelObject.getUsername());
        userDTO.setFirstname(modelObject.getFirstName());
        userDTO.setLastname(modelObject.getLastName());
        userDTO.setEmail(modelObject.getEmail());
        userDTO.setPassword(modelObject.getPassword());

        List<RoleDTO> roleList = new LinkedList<>();
        modelObject.getRoles().forEach(role -> {
            roleList.add(roleMapper.toDto(role));
        });
        userDTO.setRoles(roleList);

        return userDTO;
    }

    @Override
    public AppUser toModelFromCreation(UserDTO creationObject) {
        return null;
    }
    public AppUser toModel(RegisterDTO registerDTO) {
        return AppUser.builder()
                .username(registerDTO.getUsername())
                .firstName(registerDTO.getFirstname())
                .lastName(registerDTO.getLastname())
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .build();
    }
    public LoginSuccessDTO toLoginSuccessDTO(AppUser user, String jwt) {
        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO();
        loginSuccessDTO.setJwt(jwt);
        loginSuccessDTO.setUsername(user.getUsername());
        List<String> roles = new LinkedList<>();
        user.getRoles().forEach(role -> {
            roles.add(role.getName().toString());
        });

        loginSuccessDTO.setRoles(roles);
        return loginSuccessDTO;
    }
}