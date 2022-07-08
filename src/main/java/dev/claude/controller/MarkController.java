package dev.claude.controller;

import dev.claude.api.MarkApi;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Mark")
@RestController
public class MarkController implements MarkApi {
}
