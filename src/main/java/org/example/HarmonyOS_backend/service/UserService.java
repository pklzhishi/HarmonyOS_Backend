package org.example.HarmonyOS_backend.service;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.vo.UserLoginVo;

public interface UserService {
    int register(RegisterDto registerDto);

    Result<UserLoginVo> login(LoginDto loginDto);
}
