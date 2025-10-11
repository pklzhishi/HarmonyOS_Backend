package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comments")
public class Comments {
    private int id;
    private String content;
    private int imageId;
    private int userId;
    private String commentsTime;
    private int isDelete;
}