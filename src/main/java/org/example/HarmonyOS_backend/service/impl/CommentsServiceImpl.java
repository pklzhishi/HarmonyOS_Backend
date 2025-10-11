package org.example.HarmonyOS_backend.service.impl;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.CommentsMapper;
import org.example.HarmonyOS_backend.model.dto.PostCommentsDto;
import org.example.HarmonyOS_backend.service.CommentsService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public Result<T> postComments(PostCommentsDto postCommentsDto)
    {
        postCommentsDto.setUserId(UserHolder.getUserId());
        postCommentsDto.setCommentsTime(String.valueOf(LocalDateTime.now()));
        try{
            int x = commentsMapper.postComments(postCommentsDto);
            if(x <= 0)
            {
                throw new RuntimeException("评论失败，请稍后重试");
            }
            else
            {
                return Result.success();
            }
        }catch(Exception e){
            throw new RuntimeException("系统异常，评论失败", e);
        }
    }

    @Override
    public Result<T> deleteComments(int id)
    {
        try{
            int userIdDeleted = commentsMapper.getUserIdDeleted(id).getUserId();
            int user = UserHolder.getUserId();
            if(userIdDeleted == user)
            {
                try{
                    int x = commentsMapper.deleteComments(id);
                    if(x <= 0)
                    {
                        throw new RuntimeException("删除评论失败，请稍后再试");
                    }
                    else
                    {
                        return Result.success();
                    }
                }catch(Exception e){
                    throw new RuntimeException("系统异常，删除失败",e);
                }
            }
            else
            {
                return Result.error("无法删除他人评论");
            }
        }catch(Exception e){
            throw new RuntimeException("系统异常，删除失败",e);
        }
    }
}
