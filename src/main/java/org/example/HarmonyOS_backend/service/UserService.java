package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.ChangePasswordDto;
import org.example.HarmonyOS_backend.model.dto.LoginDto;
import org.example.HarmonyOS_backend.model.dto.RegisterDto;
import org.example.HarmonyOS_backend.model.vo.UserBasicInformationVo;
import org.example.HarmonyOS_backend.model.vo.UserLoginVo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int register(RegisterDto registerDto);
    Result<UserLoginVo> login(LoginDto loginDto);
    Result<String> changeUserHeadshot(MultipartFile headshotImage);
    Result<UserBasicInformationVo> getUserBasicInformation();
    Result<T> changePassword(ChangePasswordDto changePasswordDto);
}
