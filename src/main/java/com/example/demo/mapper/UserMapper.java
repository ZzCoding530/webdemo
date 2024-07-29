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

    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
    void insertUser(User user);

    @Update("UPDATE user SET name = #{name}, age = #{age} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(Long id);
}
