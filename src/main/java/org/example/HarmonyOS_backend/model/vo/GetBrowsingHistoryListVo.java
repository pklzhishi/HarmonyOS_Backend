package org.example.HarmonyOS_backend.model.vo;

import lombok.Data;

@Data
public class GetBrowsingHistoryListVo {
    private int imageId;
    private String imageName;
    private int imageOwner;
    private String imageUrl;
    private int imageLike;
    private int imageBookmark;
    private String content;
    private String imageTime;
    private String username;
    private String headshotUrl;
}
