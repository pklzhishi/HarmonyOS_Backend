package org.example.HarmonyOS_backend.model.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String userAccount;
    private String password;
    private String username;
    private String email;
}
