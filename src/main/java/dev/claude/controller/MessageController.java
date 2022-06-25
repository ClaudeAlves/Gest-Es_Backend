package dev.claude.controller;

import dev.claude.api.DefaultApi;
import dev.claude.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class MessageController implements DefaultApi {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> testOperation() {
        return ResponseEntity.of(Optional.of(userService.getUsers().toString()));

    }
}
