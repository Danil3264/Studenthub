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

    // check whether email is registered and hashing the password.
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
