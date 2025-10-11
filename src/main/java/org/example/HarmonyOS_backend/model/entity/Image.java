package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("image")
public class Image {
    private int imageId;
    private String imageName;
    private String imageOwner;
    private String imageUrl;
    private String imageLike;
    private String imageTime;
    private String isDelete;
    private String content;
}
