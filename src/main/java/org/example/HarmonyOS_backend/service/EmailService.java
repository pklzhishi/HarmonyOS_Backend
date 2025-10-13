package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;

public interface EmailService {
    Result<T> sendVerificationCode(String email);
    Result<T> verifyCode(String email, String code);
}
