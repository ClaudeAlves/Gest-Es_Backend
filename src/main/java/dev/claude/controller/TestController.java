package dev.claude.controller;

import dev.claude.api.TestApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "test")
@RestController
public class TestController implements TestApi {
}
