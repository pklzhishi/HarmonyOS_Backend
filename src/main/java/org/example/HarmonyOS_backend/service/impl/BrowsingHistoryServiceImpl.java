package org.example.HarmonyOS_backend.service.impl;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.BrowsingHistoryMapper;
import org.example.HarmonyOS_backend.mapper.UserLikeMapper;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;
import org.example.HarmonyOS_backend.model.entity.UserLike;
import org.example.HarmonyOS_backend.model.vo.GetBrowsingHistoryListVo;
import org.example.HarmonyOS_backend.model.vo.GetCommentsListVo;
import org.example.HarmonyOS_backend.service.BrowsingHistoryService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrowsingHistoryServiceImpl implements BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Autowired
    private UserLikeMapper userLikeMapper;

    private static final String header1 = "http://115.29.241.234:8000/images/";

    @Override
    public Result<List<GetBrowsingHistoryListVo>> getBrowsingHistoryList()
    {
        int userId = UserHolder.getUserId();
        try{
            List<GetBrowsingHistoryListVo> dataList = browsingHistoryMapper.getBrowsingHistoryList(userId);
            for(GetBrowsingHistoryListVo classInfo:dataList)
            {
                FindUserLikeDto findUserLikeDto = new FindUserLikeDto();
                findUserLikeDto.setImageId(classInfo.getImageId());
                findUserLikeDto.setUserId(UserHolder.getUserId());
                UserLike userLike = userLikeMapper.findUserLikeRecord(findUserLikeDto);
                if(userLike == null)
                {
                    classInfo.setIsLike(0);
                }
                else
                {
                    classInfo.setIsLike(1);
                }
                classInfo.setImageUrl(header1 + classInfo.getImageUrl());
                classInfo.setHeadshotUrl(header1 + classInfo.getHeadshotUrl());
            }
            return Result.success(dataList);
        }catch(RuntimeException e){
            throw new RuntimeException("获取浏览记录失败，请稍后再试");
        }
    }
}
