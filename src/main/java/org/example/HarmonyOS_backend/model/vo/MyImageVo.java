package org.example.HarmonyOS_backend.model.vo;

import lombok.Data;

@Data
public class MyImageVo {
    private int imageId;
    private String imageName;
    private String imageUrl;
    private String imageLike;
    private String imageTime;
    private String content;
    private int isLike;
    private int isBookmark;
}
