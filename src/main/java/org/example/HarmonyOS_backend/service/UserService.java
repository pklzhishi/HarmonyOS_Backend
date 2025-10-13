package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.vo.UserLoginVo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int register(RegisterDto registerDto);
    Result<UserLoginVo> login(LoginDto loginDto);
    Result<T> changeUserHeadshot(MultipartFile headshotUrl);
}
