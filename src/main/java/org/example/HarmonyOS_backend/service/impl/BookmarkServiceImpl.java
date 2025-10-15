package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.BookmarkMapper;
import org.example.HarmonyOS_backend.model.dto.FindBookmarkDto;
import org.example.HarmonyOS_backend.model.entity.UserLike;
import org.example.HarmonyOS_backend.model.vo.GetBookmarkVo;
import org.example.HarmonyOS_backend.service.BookmarkService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {
    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Override
    public Result<String> changeBookmark(FindBookmarkDto findBookmarkDto)
    {
        findBookmarkDto.setUserId(UserHolder.getUserId());
        UserLike userLike = bookmarkMapper.findBookmarkRecord(findBookmarkDto);
        if(userLike == null)
        {
            try{
                int x = bookmarkMapper.insertBookmarkRecord(findBookmarkDto);
                int x1 = bookmarkMapper.addBookmark(findBookmarkDto.getImageId());
                if(x <= 0)
                {
                    throw new RuntimeException("收藏失败，请稍后重试");
                }
                else
                {
                    return Result.success("收藏成功");
                }
            } catch (Exception e) {
                log.error("用户收藏数据库操作异常", e);
                throw new RuntimeException("系统异常，收藏失败", e);
            }
        }
        else
        {
            try{
                int x = bookmarkMapper.deleteBookmarkRecord(findBookmarkDto);
                int x1 = bookmarkMapper.reduceBookmark(findBookmarkDto.getImageId());
                if(x <= 0)
                {
                    throw new RuntimeException("取消收藏失败，请稍后重试");
                }
                else
                {
                    return Result.success("取消收藏成功");
                }
            } catch (Exception e) {
                log.error("用户取消收藏数据库操作异常", e);
                throw new RuntimeException("系统异常，取消收藏失败", e);
            }
        }
    }

    @Override
    public Result<List<GetBookmarkVo>> getBookmarkList(){
        int userId = UserHolder.getUserId();
        try{
            List<GetBookmarkVo> dataList = bookmarkMapper.getBookmarkList(userId);
            return Result.success(dataList);
        }catch(RuntimeException e) {
            throw new RuntimeException("获取失败，请稍后再试");
        }
    }
}
