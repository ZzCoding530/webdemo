package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static final String USER_CACHE_PREFIX = "user:";

    public User getUserById(Long id) {
        // 尝试从 Redis 中获取用户
        User user = (User) redisService.getValue(USER_CACHE_PREFIX + id);
        if (user == null) {
            // 如果 Redis 中没有数据，从 MySQL 中获取
            user = userMapper.getUserById(id);
            if (user != null) {
                // 将数据缓存到 Redis
                redisService.setValue(USER_CACHE_PREFIX + id, user);
            }
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
        // 插入数据后，更新 Redis 缓存
        redisService.setValue(USER_CACHE_PREFIX + user.getId(), user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
        // 更新数据后，更新 Redis 缓存
        redisService.setValue(USER_CACHE_PREFIX + user.getId(), user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
        // 删除数据后，删除 Redis 缓存
        redisService.deleteValue(USER_CACHE_PREFIX + id);
    }
}
