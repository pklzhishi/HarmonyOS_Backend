package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.PostCommentsDto;
import org.example.HarmonyOS_backend.model.vo.GetCommentsListVo;

import java.util.List;

public interface CommentsService {
    Result<T> postComments(PostCommentsDto postCommentsDto);

    Result<T> deleteComments(int imageId);
    Result<List<GetCommentsListVo>> getCommentsList(int imageId);
}
