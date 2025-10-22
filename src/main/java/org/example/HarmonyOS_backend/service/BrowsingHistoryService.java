package org.example.HarmonyOS_backend.service;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.vo.GetBookmarkVo;
import org.example.HarmonyOS_backend.model.vo.GetBrowsingHistoryListVo;

import java.util.List;

public interface BrowsingHistoryService {
    Result<List<GetBrowsingHistoryListVo>> getBrowsingHistoryList();
}
