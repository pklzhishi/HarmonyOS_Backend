package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.PostCommentsDto;

public interface CommentsService {
    Result<T> postComments(PostCommentsDto postCommentsDto);

    Result<T> deleteComments(int imageId);
}
