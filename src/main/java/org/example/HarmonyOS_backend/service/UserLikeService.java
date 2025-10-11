package org.example.HarmonyOS_backend.service;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;

public interface UserLikeService {
    Result<String> changeUserLike(FindUserLikeDto findUserLikeDto);
}
