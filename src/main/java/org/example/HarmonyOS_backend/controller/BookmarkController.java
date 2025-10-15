package org.example.HarmonyOS_backend.controller;

import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.FindBookmarkDto;
import org.example.HarmonyOS_backend.model.vo.GetBookmarkVo;
import org.example.HarmonyOS_backend.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/bookmark")
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/changeBookmark")
    public Result<String> changeBookmark(@RequestBody FindBookmarkDto findBookmarkDto)
    {
        return bookmarkService.changeBookmark(findBookmarkDto);
    }

    @GetMapping("/getBookmarkList")
    public Result<List<GetBookmarkVo>> getBookmarkList()
    {
        return bookmarkService.getBookmarkList();
    }
}
