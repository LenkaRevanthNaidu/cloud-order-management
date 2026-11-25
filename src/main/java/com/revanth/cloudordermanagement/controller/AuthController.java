
package com.revanth.cloudordermanagement.controller;

import com.revanth.cloudordermanagement.dto.RegisterRequest;
import com.revanth.cloudordermanagement.dto.LoginRequest;
import com.revanth.cloudordermanagement.entity.User;
import com.revanth.cloudordermanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        return Map.of(
                "message", "User registered successfully",
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        );
    }


    @PostMapping("/login")
    public Map<String, Object> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return Map.of(
                "message", "Login successful",
                "token", token
        );
    }

}
