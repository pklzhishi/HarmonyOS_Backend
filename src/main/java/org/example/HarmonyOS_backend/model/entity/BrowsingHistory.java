package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("browsing_history")
public class BrowsingHistory {
    private int id;
    private int imageId;
    private int userId;
    private String browsingTime;
    private int isDelete;
}
