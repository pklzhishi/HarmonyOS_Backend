package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.UserLikeMapper;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;
import org.example.HarmonyOS_backend.model.entity.UserLike;
import org.example.HarmonyOS_backend.service.UserLikeService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserLikeServiceImpl implements UserLikeService {
    @Autowired
    private UserLikeMapper userLikeMapper;

    @Override
    public Result<String> changeUserLike(FindUserLikeDto findUserLikeDto)
    {
        findUserLikeDto.setUserId(UserHolder.getUserId());
        UserLike userLike = userLikeMapper.findUserLikeRecord(findUserLikeDto);
        if(userLike == null)
        {
            try{
                int x = userLikeMapper.insertUserLikeRecord(findUserLikeDto);
                int x1 = userLikeMapper.addLike(findUserLikeDto.getImageId());
                if(x <= 0)
                {
                    throw new RuntimeException("点赞失败，请稍后重试");
                }
                else
                {
                    return Result.success("点赞成功");
                }
            } catch (Exception e) {
                log.error("用户点赞数据库操作异常", e);
                throw new RuntimeException("系统异常，点赞失败", e);
            }
        }
        else
        {
            try{
                int x = userLikeMapper.deleteUserLikeRecord(findUserLikeDto);
                int x1 = userLikeMapper.reduceLike(findUserLikeDto.getImageId());
                if(x <= 0)
                {
                    throw new RuntimeException("取消点赞失败，请稍后重试");
                }
                else
                {
                    return Result.success("取消点赞成功");
                }
            } catch (Exception e) {
                log.error("用户取消点赞数据库操作异常", e);
                throw new RuntimeException("系统异常，取消点赞失败", e);
            }
        }
    }
}
