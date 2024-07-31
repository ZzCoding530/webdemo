package com.example.demo.service;

public interface RedisService {
    void setValue(String key, Object value);
    Object getValue(String key);
    void deleteValue(String key);
}