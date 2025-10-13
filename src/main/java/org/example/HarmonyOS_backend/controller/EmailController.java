package org.example.HarmonyOS_backend.controller;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendVerificationCode")
    public Result<T> sendVerificationCode(@RequestParam String email)
    {
        return emailService.sendVerificationCode(email);
    }
}
