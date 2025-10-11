package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.constan.JwtClaimsConstant;
import org.example.HarmonyOS_backend.constan.JwtProperties;
import org.example.HarmonyOS_backend.mapper.UserMapper;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.entity.User;
import org.example.HarmonyOS_backend.model.vo.UserLoginVo;
import org.example.HarmonyOS_backend.service.UserService;
import org.example.HarmonyOS_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    @Override
    public int register(RegisterDto registerDto)
    {
        User user = userMapper.getUserByAccount(registerDto.getUserAccount());
        if(user != null)
        {
            return 0;
        }
        try {
            registerDto.setPassword(md5(registerDto.getPassword()));
            int insertRows = userMapper.insertUser(registerDto);
            if (insertRows <= 0) {
                log.error("用户注册失败，数据库插入异常: {}", registerDto.getUserAccount());
                throw new RuntimeException("注册失败，请稍后重试");
            }
            log.info("用户注册成功: {}", registerDto.getUserAccount());
            return 1;
        } catch (Exception e) {
            log.error("用户注册数据库操作异常", e);
            throw new RuntimeException("系统异常，注册失败", e);
        }
    }

    @Override
    public Result<UserLoginVo> login(LoginDto loginDto)
    {
        try{
            loginDto.setPassword(md5(loginDto.getPassword()));
            User user = userMapper.login(loginDto);
            if(user == null)
            {
                return Result.error("用户名或密码错误");
            }
            else
            {
                Map<String, Object> claims = new HashMap<>();
                claims.put(JwtClaimsConstant.userId, user.getUserId());
                String token = JwtUtil.createJWT(
                        jwtProperties.getUserSecretKey(),
                        jwtProperties.getUserTtl(),
                        claims);
                UserLoginVo userLoginVo = UserLoginVo.builder()
                        .userId(user.getUserId())
                        .userAccount(user.getUserAccount())
                        .username(user.getUsername())
                        .headshotUrl(user.getHeadshotUrl())
                        .telephoneNumber(user.getTelephoneNumber())
                        .token(token)
                        .build();
                return Result.success(userLoginVo);
            }
        }catch(Exception e) {
            log.error("用户登录数据库操作异常", e);
            throw new RuntimeException("系统异常，登录失败", e);
        }
    }
}
