package com.studenthub.user;

import com.studenthub.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepository;

    public void changeName(User user, String newName){
//        userRepository.       ;
    }


    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found with id: " + id));
        return userResponseMapper(user);
    }
    public UserResponse findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found with email: " + email));
        return userResponseMapper(user);
    }

    private UserResponse userResponseMapper(User user){
        return new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getYearOfStudy(), user.getEmail(), user.getRole());
    }

}
