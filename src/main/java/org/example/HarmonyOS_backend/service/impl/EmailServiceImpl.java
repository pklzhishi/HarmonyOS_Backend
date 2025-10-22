package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 从配置文件获取发送者邮箱
    @Value("${spring.mail.username}")
    private String fromEmail;

    // 从配置文件获取验证码过期时间
    @Value("${verification.expire-seconds}")
    private int expireSeconds;

    // 从配置文件获取验证码长度
    @Value("${verification.code-length}")
    private int codeLength;

    @Override
    public Result<T> sendVerificationCode(String email) {
        try {
            // 生成指定长度的随机验证码
            String code = generateVerificationCode();

            // 发送邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("【验证码通知】");
            message.setText("您的验证码是：" + code + "，有效期" + (expireSeconds/60) + "分钟，请尽快使用。");
            mailSender.send(message);

            // 存储验证码到Redis，设置过期时间
            redisTemplate.opsForValue().set(
                    "verify_code:" + email,
                    code,
                    expireSeconds,
                    TimeUnit.SECONDS
            );

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("发送失败");
        }
    }

    @Override
    public Result<T> verifyCode(String email, String code) {
        String key = "verify_code:" + email;
        String storedCode = redisTemplate.opsForValue().get(key);

        if (storedCode != null && storedCode.equals(code)) {
            // 验证成功后删除验证码
            redisTemplate.delete(key);
            return Result.success();
        }
        return Result.error("验证码错误");
    }

    // 生成指定长度的随机验证码
    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

}
