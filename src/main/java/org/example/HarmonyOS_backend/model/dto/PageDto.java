package org.example.HarmonyOS_backend.model.dto;

import lombok.Data;

@Data

public class PageDto {
    private String text;
    private Integer page;
    private Integer size;
}
