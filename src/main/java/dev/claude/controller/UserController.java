package dev.claude.controller;

import dev.claude.api.UserApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "user")
@RestController
public class UserController implements UserApi {

}
