package org.example.HarmonyOS_backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.vo.UserLoginVo;
import org.example.HarmonyOS_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto){
        try{
            int x = userService.register(registerDto);
            if(x == 1)
            {
                return Result.success();
            }
            else {
                return Result.error("账号已存在");
            }
        }catch(RuntimeException e){
            return Result.error(500,e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<UserLoginVo> login(@RequestBody LoginDto loginDto){
        try{
            return userService.login(loginDto);
        }catch(Exception e){
            return Result.error(500,e.getMessage());
        }
    }

    @PostMapping("/changeUserHeadshot")
    public Result<T> changeUserHeadshot(MultipartFile headshotUrl)
    {
        return userService.changeUserHeadshot(headshotUrl);
    }
}
