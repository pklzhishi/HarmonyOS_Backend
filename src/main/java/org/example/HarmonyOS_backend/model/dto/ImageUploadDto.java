package org.example.HarmonyOS_backend.model.dto;

import lombok.Data;

@Data
public class ImageUploadDto {
    private String imageName;
    private int imageOwner;
    private String imageUrl;
    private String imageTime;
    private String content;
}
