package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.ImageMapper;
import org.example.HarmonyOS_backend.model.dto.GetMyImageDto;
import org.example.HarmonyOS_backend.model.dto.ImageUploadDto;
import org.example.HarmonyOS_backend.model.vo.GetImageRandomlyVo;
import org.example.HarmonyOS_backend.model.vo.MyImageVo;
import org.example.HarmonyOS_backend.service.ImageService;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.commons.compress.harmony.pack200.PackingUtils.log;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    private ImageUploadDto imageUploadDto = new ImageUploadDto();
    private static final String header = "D:/upload/images/";
//    private static final String header = "/opt/HarmonyOS/upload/images/";
    private static final String header1 = "http://10.34.62.85:8000/images/";
//    private static final String header1 = "http://115.29.241.234:8000/images/";
//    public Result<String> imageUplode(String base64Image, String imageName){
//        // 1. 校验Base64字符串是否为空
//        log(base64Image);
//        if (base64Image == null || base64Image.trim().isEmpty()) {
//            return Result.error("上传失败：图片数据为空");
//        }
//
//        try {
//            // 2. 提取Base64数据（去除前缀如"data:image/jpeg;base64,"）
//            String[] base64Parts = base64Image.split(",");
//            String imageData = base64Parts.length > 1 ? base64Parts[1] : base64Parts[0];
//
//            // 3. 校验文件类型
//            String fileType = getFileTypeFromBase64Header(base64Image);
//            if (fileType == null || !fileType.matches("jpg|jpeg|png|gif")) {
//                return Result.error("上传失败：仅支持 jpg、jpeg、png、gif 等图片格式");
//            }
//
//            // 4. 生成唯一文件名
//            String fileName = UUID.randomUUID().toString() + "." + fileType;
//
//            // 5. 创建保存目录（若不存在则创建）
//            File saveDir = new File(header);
//            if (!saveDir.exists()) {
//                saveDir.mkdirs(); // 递归创建目录
//            }
//
//            // 6. 解码Base64并保存为文件
//            byte[] imageBytes = Base64.getDecoder().decode(imageData);
//            File destFile = new File(header + fileName);
//
//            try (FileOutputStream fos = new FileOutputStream(destFile)) {
//                fos.write(imageBytes);
//            }
//
//            // 7. 保存记录到数据库
//            imageUploadDto.setImageName(imageName);
//            imageUploadDto.setImageOwner(UserHolder.getUserId());
//            imageUploadDto.setImageUrl(fileName); // 保存文件名
//            imageUploadDto.setImageTime(String.valueOf(LocalDateTime.now()));
//
//            int insertRows = imageMapper.insertImage(imageUploadDto);
//            if (insertRows <= 0) {
//                ImageServiceImpl.log.error("图片上传失败，数据库插入异常");
//                // 数据库操作失败时删除已保存的文件
//                if (destFile.exists()) {
//                    destFile.delete();
//                }
//                throw new RuntimeException("图片上传失败，请稍后重试");
//            }
//
//            // 8. 返回成功结果，包含完整访问URL
//            return Result.success(header1 + fileName);
//
//        } catch (IllegalArgumentException e) {
//            ImageServiceImpl.log.error("Base64解码失败", e);
//            return Result.error("上传失败：图片数据格式错误");
//        } catch (IOException e) {
//            ImageServiceImpl.log.error("文件保存失败", e);
//            return Result.error("上传失败：" + e.getMessage());
//        } catch (Exception e) {
//            ImageServiceImpl.log.error("图片上传异常", e);
//            return Result.error("系统异常，图片上传失败");
//        }
//    }
//
//    /**
//     * 从Base64头部信息获取文件类型
//     */
//    private String getFileTypeFromBase64Header(String base64Image) {
//        if (base64Image.startsWith("data:image/jpeg;base64,")) {
//            return "jpg";
//        } else if (base64Image.startsWith("data:image/png;base64,")) {
//            return "png";
//        } else if (base64Image.startsWith("data:image/gif;base64,")) {
//            return "gif";
//        }
//        return null;
//    }
    public Result<T> imageUplode(MultipartFile file, String imageName,String content)
    {
        // 1. 校验文件是否为空
        if (file.isEmpty()) {
            return Result.error("上传失败：请选择文件");
        }

        // 2. 校验文件类型（仅允许图片格式）
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            return Result.error("上传失败：仅支持 jpg、jpeg、png、gif 等图片格式");
        }

        // 3. 生成唯一文件名（避免重名覆盖）
        String fileName = UUID.randomUUID().toString()
                + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 4. 创建保存目录（若不存在则创建）
        File saveDir = new File(header);
        if (!saveDir.exists()) {
            saveDir.mkdirs(); // 递归创建目录
        }

        // 5. 保存文件到本地
        try {
            File destFile = new File(header + fileName);
            file.transferTo(destFile); // 将上传的文件保存到目标路径
            // 返回图片访问路径（实际项目中可配置为服务器 URL）
            try {
                imageUploadDto.setImageName(imageName);
                imageUploadDto.setImageOwner(UserHolder.getUserId());
                imageUploadDto.setImageUrl(fileName);
                imageUploadDto.setImageTime(String.valueOf(LocalDateTime.now()));
                imageUploadDto.setContent(content);
                int insertRows = imageMapper.insertImage(imageUploadDto);
                if (insertRows <= 0) {
                    log.error("图片上传失败，数据库插入异常");
                    throw new RuntimeException("图片上传失败，请稍后重试");
                }
                return Result.success();
            } catch (Exception e) {
                log.error("图片上传数据库操作异常", e);
                throw new RuntimeException("系统异常，图片上传失败", e);
            }
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }


    @Override
    public Result<List<MyImageVo>> getMyImage(GetMyImageDto getMyImageDto)
    {
        int imageOwner = UserHolder.getUserId();
        int page = getMyImageDto.getPage();
        int size = getMyImageDto.getSize();
        List<MyImageVo> dataList = imageMapper.getMyImage(imageOwner);
        for(MyImageVo classInfo : dataList)
        {
            classInfo.setImageUrl(header1 + classInfo.getImageUrl());
        }
        int start = (page - 1) * size;
        int end = Math.min(start + size, dataList.size());
        if (start >= dataList.size()) {
            return Result.error("不存在的页数");
        }
        return Result.success(dataList.subList(start,end));
    }

    @Override
    public Result<Map<String, Integer>> getMyImageNumber()
    {
        int imageOwner = UserHolder.getUserId();
        Map<String, Integer> map = new HashMap<>();
        map.put("number",imageMapper.getMyImageNumber(imageOwner));
        return Result.success(map);
    }

    @Override
    public Result<List<GetImageRandomlyVo>> getImageRandomly(int count)
    {
        int maxCount = imageMapper.getAllImageNumber();
        if(count > maxCount)
        {
            count = maxCount;
        }
        List<GetImageRandomlyVo> dataList = imageMapper.getImageRandomly(count);
        for(GetImageRandomlyVo classInfo : dataList)
        {
            classInfo.setImageUrl(header1 + classInfo.getImageUrl());
            classInfo.setHeadshotUrl(header1 + classInfo.getHeadshotUrl());
        }
        return Result.success(dataList);
    }

    @Override
    public Result<List<GetImageRandomlyVo>> searchImage(String imageName)
    {
        List<GetImageRandomlyVo> dataList = imageMapper.searchImage(imageName);
        for(GetImageRandomlyVo classInfo : dataList)
        {
            classInfo.setImageUrl(header1 + classInfo.getImageUrl());
            classInfo.setHeadshotUrl(header1 + classInfo.getHeadshotUrl());
        }
        return Result.success(dataList);
    }
}
