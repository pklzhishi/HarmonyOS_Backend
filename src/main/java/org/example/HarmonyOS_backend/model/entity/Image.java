package org.example.HarmonyOS_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("image")
public class Image {
    private int imageId;
    private String imageName;
    private int imageOwner;
    private String imageUrl;
    private String imageLike;
    private String imageTime;
    private int isDelete;
    private String content;
}
