package org.example.HarmonyOS_backend.service.impl;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.BrowsingHistoryMapper;
import org.example.HarmonyOS_backend.model.vo.GetBrowsingHistoryListVo;
import org.example.HarmonyOS_backend.service.BrowsingHistoryService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrowsingHistoryServiceImpl implements BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    public Result<List<GetBrowsingHistoryListVo>> getBrowsingHistoryList()
    {
        int userId = UserHolder.getUserId();
        try{
            List<GetBrowsingHistoryListVo> dataList = browsingHistoryMapper.getBrowsingHistoryList(userId);
            return Result.success(dataList);
        }catch(RuntimeException e){
            throw new RuntimeException("获取浏览记录失败，请稍后再试");
        }
    }
}
