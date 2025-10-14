package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.constan.JwtClaimsConstant;
import org.example.HarmonyOS_backend.constan.JwtProperties;
import org.example.HarmonyOS_backend.mapper.UserMapper;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.entity.User;
import org.example.HarmonyOS_backend.model.vo.UserLoginVo;
import org.example.HarmonyOS_backend.service.UserService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.example.HarmonyOS_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

//    private static final String header = "D:/upload/images/";
        private static final String header = "/opt/HarmonyOS/upload/images/";
//    private static final String header1 = "http://192.168.99.40:8000/images/";
    private static final String header1 = "http://115.29.241.234:8000/images/";

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

    @Override
    public Result<T> changeUserHeadshot(MultipartFile headshotImage)
    {
        File tempSavedFile = null;
        try {
            if (headshotImage.isEmpty()) {
                return Result.error("上传失败：请选择文件");
            }

            String originalFilename = headshotImage.getOriginalFilename();
            System.out.println("----------------------------------");
            System.out.println(originalFilename);
            System.out.println("----------------------------------");
            if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png|gif)$")) {
                return Result.error("上传失败：仅支持 jpg、jpeg、png、gif 等图片格式");
            }

            // 3. 关键步骤：读取前端传递的Base64文本，解码为二进制图片数据
            // 3.1 先将MultipartFile的内容转为字符串（因为前端传的是Base64文本）
            String base64Text = new String(headshotImage.getBytes(), StandardCharsets.UTF_8);
            // 3.2 去除Base64前缀（若前端传的是带前缀格式，如"data:image/png;base64,iVBORw0KGgo..."）
//            String pureBase64 = base64Text.replaceAll("^data:image/[^;]+;base64,", "");
            // 3.3 Base64解码为二进制（核心：将文本转为图片二进制）
            byte[] imageBinaryData;
            try {
                imageBinaryData = Base64.getDecoder().decode(base64Text);
            } catch (IllegalArgumentException e) {
                log.error("Base64解码失败：文本不是有效Base64格式", e);
                return Result.error("文件编码错误，请重新上传");
            }

            File saveDir = new File(header);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            String fileName = UUID.randomUUID().toString()
                    + originalFilename.substring(originalFilename.lastIndexOf("."));

            tempSavedFile = new File(saveDir, fileName); // 拼接完整路径

            try (FileOutputStream fos = new FileOutputStream(tempSavedFile)){
                fos.write(imageBinaryData); // 写入解码后的二进制数据
                fos.flush();
                try {
                    int insertRows = userMapper.changeUserHeadshot(fileName,UserHolder.getUserId());
                    if (insertRows <= 0) {
                        log.error("头像上传失败，数据库插入异常");
                        throw new RuntimeException("头像上传失败，请稍后重试");
                    }
                    return Result.success();
                } catch (Exception e) {
                    log.error("头像上传数据库操作异常", e);
                    throw new RuntimeException("系统异常，头像上传失败", e);
                }
            } catch (IOException e) {
                return Result.error("上传失败：" + e.getMessage());
            }
        }catch (IOException e) {
            log.error("文件保存异常", e);
            return Result.error("文件保存失败，请检查服务器磁盘");
        } catch (Exception e) {
            log.error("上传系统异常", e);
            return Result.error("系统异常，请重试");
        }
    }
}
