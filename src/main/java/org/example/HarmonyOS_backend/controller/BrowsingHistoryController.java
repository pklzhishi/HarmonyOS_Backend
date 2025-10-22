package org.example.HarmonyOS_backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.vo.GetBrowsingHistoryListVo;
import org.example.HarmonyOS_backend.service.BrowsingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/bookmark")
public class BrowsingHistoryController {
    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    @GetMapping("/getBrowsingHistoryList")
    public Result<List<GetBrowsingHistoryListVo>> getBrowsingHistoryList()
    {
        return browsingHistoryService.getBrowsingHistoryList();
    }
}
