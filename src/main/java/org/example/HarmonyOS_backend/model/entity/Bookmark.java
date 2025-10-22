package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bookmark")
public class Bookmark {
    private int id;
    private int imageId;
    private int userId;
    private int isDelete;
}
