package org.example.HarmonyOS_backend.model.vo;

import lombok.Data;

@Data
public class GetCommentsListVo {
    private int id;
    private int userId;
    private String content;
    private String commentsTime;
    private String username;
    private String headshotUrl;
}
