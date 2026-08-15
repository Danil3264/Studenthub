package com.studenthub.user;

public class UserService {
    public void changeName(User user, String newName){
        user.setName(newName);
    }

    public User getUser(User user) {
        return user;
    }

    // make after the authorization

// Change to work with the database - removeById()
//    public void removeUser(User user){
//        if(user == null) return;
//        user.setName(null);
//        user.setSurname(null);
//        user.setEmail(null);
//        user = null;
//    }
}
