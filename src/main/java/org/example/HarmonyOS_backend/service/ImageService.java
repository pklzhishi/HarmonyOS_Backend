package org.example.HarmonyOS_backend.service;

import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.model.dto.GetMyImageDto;
import org.example.HarmonyOS_backend.model.vo.GetImageRandomlyVo;
import org.example.HarmonyOS_backend.model.vo.MyImageVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ImageService {
    Result<T> imageUplode(MultipartFile file, String imageName,String content);
//    Result<String> imageUplode(String base64Image, String imageName);
    Result<List<MyImageVo>> getMyImage(GetMyImageDto getMyImageDto);

    Result<Map<String, Integer>> getMyImageNumber();

    Result<List<GetImageRandomlyVo>> getImageRandomly(int count);

    Result<List<GetImageRandomlyVo>> searchImage(String imageName);
}
