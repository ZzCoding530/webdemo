package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // 定义一个名为 redisTemplate 的 Bean，用于配置 RedisTemplate
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建一个新的 RedisTemplate 实例
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置 Redis 连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 设置键的序列化器为 StringRedisSerializer
        // 这样键会被序列化为字符串
        template.setKeySerializer(new StringRedisSerializer());

        // 设置值的序列化器为 GenericJackson2JsonRedisSerializer
        // 这样值会被序列化为 JSON 格式
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 返回配置好的 RedisTemplate 实例
        return template;
    }
}
