package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(Long id);

    @Select("SELECT * FROM user")
    List<User> getAllUsers();

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User getUserByEmail(String email);

    @Insert("INSERT INTO user(name, age, username, password, email) VALUES(#{name}, #{age}, #{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Update("UPDATE user SET name = #{name}, age = #{age}, username = #{username}, password = #{password}, email = #{email} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(Long id);

    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    int countByUsername(String username);

    @Select("SELECT COUNT(*) FROM user WHERE email = #{email}")
    int countByEmail(String email);
}