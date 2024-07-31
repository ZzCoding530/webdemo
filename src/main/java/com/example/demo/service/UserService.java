package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    boolean isUsernameAvailable(String username);
    boolean isEmailAvailable(String email);
}