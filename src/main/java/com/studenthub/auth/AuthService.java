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

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(request.getPassword(),
                user.getPasswordHash());
    }

    public boolean register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            return false;
        }

        String rawPassword = request.getPassword();
        String Hash = hashPassword(rawPassword);

        //save to DB
        // ...

        return false;
    }


    // Hash the password before saving to your database
    public String hashPassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

}
