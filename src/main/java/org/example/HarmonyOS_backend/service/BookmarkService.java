package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.FindBookmarkDto;
import org.example.HarmonyOS_backend.model.vo.GetBookmarkVo;

import java.util.List;

public interface BookmarkService {
    Result<String> changeBookmark(FindBookmarkDto findBookmarkDto);

    Result<List<GetBookmarkVo>> getBookmarkList();
}
