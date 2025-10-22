package org.example.HarmonyOS_backend.controller;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;
import org.example.HarmonyOS_backend.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/userLike")
public class UserLikeController {
    @Autowired
    private UserLikeService userLikeService;

    @PostMapping("changeUserLike")
    public Result<String> changeUserLike(@RequestBody FindUserLikeDto findUserLikeDto)
    {
        return userLikeService.changeUserLike(findUserLikeDto);
    }
}
