package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE user_account = #{userAccount} AND is_delete = 0")
    User getUserByAccount(String userAccount);

    @Insert("INSERT INTO user(user_account,password,username,telephone_number) VALUES (#{userAccount},#{password},#{username},#{telephoneNumber})")
    int insertUser(RegisterDto registerDto);

    @Select("SELECT * FROM user WHERE user_account = #{userAccount} AND password = #{password}")
    User login(LoginDto loginDto);

    @Update("UPDATE user SET headshot_url = #{headshotUrl} WHERE user_id = #{userId}")
    int changeUserHeadshot(String headshotUrl,int userId);
}