package org.example.HarmonyOS_backend.model.vo;

import lombok.Data;

@Data
public class GetImageRandomlyVo {
    private int imageId;
    private int userId;
    private String imageName;
    private int imageOwner;
    private String imageUrl;
    private int imageLike;
    private String imageTime;
    private String content;
    private String username;
    private String headshotUrl;
    private int isLike;
    private int isBookmark;
}
