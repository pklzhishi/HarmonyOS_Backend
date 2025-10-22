package org.example.HarmonyOS_backend.model.dto;

import lombok.Data;

@Data
public class VerifyCodeDto {
    private String email;
    private String code;
}
