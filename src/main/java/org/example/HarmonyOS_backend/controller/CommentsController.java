package org.example.HarmonyOS_backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.CommentsListDto;
import org.example.HarmonyOS_backend.model.dto.PostCommentsDto;
import org.example.HarmonyOS_backend.model.entity.Comments;
import org.example.HarmonyOS_backend.model.vo.GetCommentsListVo;
import org.example.HarmonyOS_backend.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @PostMapping("/postComments")
    public Result<T> postComments(@RequestBody PostCommentsDto postCommentsDto)
    {
        return commentsService.postComments(postCommentsDto);
    }

    @PostMapping("/deleteComments")
    public Result<T> deleteComments(@RequestBody Comments comments)
    {
        return commentsService.deleteComments(comments.getId());
    }

    @PostMapping("/getCommentsList")
    public Result<List<GetCommentsListVo>> getCommentsList(@RequestBody CommentsListDto commentsListDto)
    {
        return commentsService.getCommentsList(commentsListDto.getImageId());
    }
}