package org.example.HarmonyOS_backend.controller;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.GetMyImageDto;
import org.example.HarmonyOS_backend.model.vo.GetImageRandomlyVo;
import org.example.HarmonyOS_backend.model.vo.MyImageVo;
import org.example.HarmonyOS_backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/imageUpload")
    public Result<T> imageUpload(@RequestParam MultipartFile multipartFile, @RequestParam String imageName,@RequestParam(required = false,defaultValue = "") String content)
    {
        return imageService.imageUplode(multipartFile,imageName,content);
    }

    @PostMapping("/getMyImage")
    public Result<List<MyImageVo>> getMyImage(@RequestBody GetMyImageDto getMyImageDto)
    {
        return imageService.getMyImage(getMyImageDto);
    }

    @GetMapping("/getMyImageNumber")
    public Result<Map<String, Integer>> getMyImageNumber()
    {
        return imageService.getMyImageNumber();
    }

    @PostMapping("/getImageRandomly")
    public Result<List<GetImageRandomlyVo>> getImageRandomly(@RequestParam int count)
    {
        return imageService.getImageRandomly(count);
    }

    @PostMapping("/searchImage")
    public Result<List<GetImageRandomlyVo>> searchImage(@RequestParam String imageName)
    {
        return imageService.searchImage(imageName);
    }

    @PostMapping("/deleteImage")
    public Result<T> deleteImage(@RequestParam int imageId)
    {
        return imageService.deleteImage(imageId);
    }
}
