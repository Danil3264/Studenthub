package com.studenthub.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void changeName(User user, String newName){
//        userRepository.       ;
    }


    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    // Change to work with the database - removeById()
    //    public void removeUser(User user){

}
