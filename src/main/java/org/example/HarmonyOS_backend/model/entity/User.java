package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private int userId;
    private String userAccount;
    private String password;
    private String username;
    private String headshotUrl;
    private String telephoneNumber;
    private String isDelete;
}