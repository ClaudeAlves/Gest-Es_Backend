package dev.claude.controller;


import dev.claude.api.AuthenticationApi;
import dev.claude.domain.user.AppUser;
import dev.claude.domain.user.EnumRole;
import dev.claude.dto.ApiMessageDTO;
import dev.claude.dto.LoginRequestDTO;
import dev.claude.dto.LoginSuccessDTO;
import dev.claude.dto.RegisterDTO;
import dev.claude.mapper.user.UserMapper;
import dev.claude.service.UserService;
import dev.claude.service.exception.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Api(tags = "authentication")
@RestController
@Slf4j
public class AuthenticationController implements AuthenticationApi {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<ApiMessageDTO> register(@Valid RegisterDTO registerDTO) {
        try {
            AppUser user = userMapper.toModel(registerDTO);
            try {
                if(EnumRole.getRole(registerDTO.getRole()) != EnumRole.ROLE_USER
                    && EnumRole.getRole(registerDTO.getRole()) != EnumRole.ROLE_ADMIN) {
                    // here we want to register a user that's not only a basic user
                    // and is not an admin, so we add the role to the user
                    user = userService.addRole(user, EnumRole.getRole(registerDTO.getRole()));
                    userService.register(user);
                } else {
                    throw new UnauthorizedException("Cannot register an admin");
                }
            } catch (Exception e) {
                throw new WrongCredentialsException("Role does not exist");
            }
        } catch (NullPointerException e) {
            throw new IncompleteBodyException();
        }
        return ResponseEntity.ok(ApiHelper.created("Register successful"));
    }

    @Override
    public ResponseEntity<LoginSuccessDTO> login(@Valid LoginRequestDTO loginRequestDTO) {
        String jwt = userService.login(loginRequestDTO.getUsernameOrEmail(), loginRequestDTO.getPassword());
        AppUser user = userService.getCurrentUser();

        LoginSuccessDTO loginSuccessDTO = userMapper.toLoginSuccessDTO(user, jwt);

        return ResponseEntity.ok(loginSuccessDTO);
    }

    @Override
    public ResponseEntity<ApiMessageDTO> logout() {
        userService.logout();
        return ResponseEntity.ok(ApiHelper.ok("Logout successful"));
    }

}
