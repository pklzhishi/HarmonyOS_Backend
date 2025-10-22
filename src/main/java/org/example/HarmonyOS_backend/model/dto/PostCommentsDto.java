package org.example.HarmonyOS_backend.model.dto;

import lombok.Data;

@Data
public class PostCommentsDto {
    private String content;
    private int imageId;
    private int userId;
    private String commentsTime;
}
