package org.example.HarmonyOS_backend.service;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.FindBookmarkDto;

public interface BookmarkService {
    Result<String> changeBookmark(FindBookmarkDto findBookmarkDto);
}
