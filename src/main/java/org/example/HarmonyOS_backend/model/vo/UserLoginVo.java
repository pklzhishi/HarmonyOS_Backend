package org.example.HarmonyOS_backend.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVo {
    private int userId;
    private String userAccount;
    private String username;
    private String headshotUrl;
    private String email;
    private String token;
}
