package com.studenthub.auth;

import com.studenthub.auth.dto.LoginRequest;
import com.studenthub.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public boolean register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping ("/login")
    public boolean login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }
}
