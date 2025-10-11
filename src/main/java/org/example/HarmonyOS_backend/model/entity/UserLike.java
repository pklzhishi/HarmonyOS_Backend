package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_like")
public class UserLike {
    private int id;
    private int imageId;
    private int userId;
}
