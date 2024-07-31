package com.example.demo.controller;

import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value set successfully";
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        Object value = redisService.getValue(key);
        return "Value: " + (value != null ? value.toString() : "null");
    }

    @DeleteMapping("/delete")
    public String deleteValue(@RequestParam String key) {
        redisService.deleteValue(key);
        return "Value deleted successfully";
    }
}