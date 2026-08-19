package com.studenthub.auth;

import com.studenthub.auth.dto.LoginRequest;
import com.studenthub.auth.dto.RegisterRequest;
import com.studenthub.user.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash()))
            throw new RuntimeException("Invalid credentials");

        return jwtService.createToken(request.getEmail(), user.getRole().name(), user.getId().toString());
    }


    // check whether email is registered and hashing the password.
    // register() shoulds return HTTP-answer/DTO. - must be FIXED
    public boolean register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            return false;
        }

        String rawPassword = request.getPassword();
        String passwordHash = hashPassword(rawPassword);

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .yearOfStudy(request.getYearOfStudy())
                .email(request.getEmail())
                .passwordHash(passwordHash)
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);

        System.out.println("USER SAVED, ID = " + user.getId());

        return true;
    }


    // Hash the password before saving to your database
    public String hashPassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

}
