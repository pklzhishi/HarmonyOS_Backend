package org.example.HarmonyOS_backend.model.dto;

import lombok.Data;

@Data
public class InsertBrowsingHistoryDto {
    private int imageId;
    private int userId;
    private String browsingTime;
}
