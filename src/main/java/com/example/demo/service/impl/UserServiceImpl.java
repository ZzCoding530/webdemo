package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RedisService redisService;
    private static final String USER_CACHE_PREFIX = "user:";

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    @Override
    public User getUserById(Long id) {
        User user = (User) redisService.getValue(USER_CACHE_PREFIX + id);
        if (user == null) {
            user = userMapper.getUserById(id);
            if (user != null) {
                redisService.setValue(USER_CACHE_PREFIX + id, user);
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        if (userMapper.countByUsername(user.getUsername()) > 0) {
            throw new RuntimeException("Username already exists");
        }
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            throw new RuntimeException("Email already exists");
        }
        userMapper.insertUser(user);
        redisService.setValue(USER_CACHE_PREFIX + user.getId(), user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existingUser = userMapper.getUserById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        if (!existingUser.getUsername().equals(user.getUsername()) && userMapper.countByUsername(user.getUsername()) > 0) {
            throw new RuntimeException("Username already exists");
        }
        if (!existingUser.getEmail().equals(user.getEmail()) && userMapper.countByEmail(user.getEmail()) > 0) {
            throw new RuntimeException("Email already exists");
        }
        userMapper.updateUser(user);
        redisService.setValue(USER_CACHE_PREFIX + user.getId(), user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
        redisService.deleteValue(USER_CACHE_PREFIX + id);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return userMapper.countByUsername(username) == 0;
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userMapper.countByEmail(email) == 0;
    }
}