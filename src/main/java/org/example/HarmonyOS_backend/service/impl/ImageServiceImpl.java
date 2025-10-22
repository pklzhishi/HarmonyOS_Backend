package org.example.HarmonyOS_backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.apache.poi.ss.formula.functions.T;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.mapper.BookmarkMapper;
import org.example.HarmonyOS_backend.mapper.BrowsingHistoryMapper;
import org.example.HarmonyOS_backend.mapper.ImageMapper;
import org.example.HarmonyOS_backend.mapper.UserLikeMapper;
import org.example.HarmonyOS_backend.model.dto.*;
import org.example.HarmonyOS_backend.model.entity.*;
import org.example.HarmonyOS_backend.model.vo.GetImageInformationVo;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.commons.compress.harmony.pack200.PackingUtils.log;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Autowired
    private UserLikeMapper userLikeMapper;
    @Autowired
    private BookmarkMapper bookmarkMapper;
    private ImageUploadDto imageUploadDto = new ImageUploadDto();
//    private static final String header = "D:/upload/images/";
    private static final String header = "/opt/HarmonyOS/upload/images/";
//    private static final String header1 = "http://10.34.18.43:8000/images/";
    private static final String header1 = "http://115.29.241.234:8000/images/";

    public Result<T> imageUplode1(MultipartFile file, String imageName,String content)
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


    public Result<T> imageUplode(MultipartFile multipartFile, String imageName,String content)
    {
        File tempSavedFile = null;
        try {
            if (multipartFile.isEmpty()) {
                return Result.error("上传失败：请选择文件");
            }

            String originalFilename = multipartFile.getOriginalFilename();
            System.out.println("----------------------------------");
            System.out.println(originalFilename);
            System.out.println("----------------------------------");
            if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png|gif)$")) {
                return Result.error("上传失败：仅支持 jpg、jpeg、png、gif 等图片格式");
            }

//             "3. 关键步骤：读取前端传递的Base64文本，解码为二进制图片数据"
            // 3.1 先将MultipartFile的内容转为字符串（因为前端传的是Base64文本）
            String base64Text = new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
            // 3.2 去除Base64前缀（若前端传的是带前缀格式，如"data:image/png;base64,iVBORw0KGgo..."）
//            String pureBase64 = base64Text.replaceAll("^data:image/[^;]+;base64,", "");
//            " 3.3 Base64解码为二进制（核心：将文本转为图片二进制）"
            byte[] imageBinaryData;
            try {
                imageBinaryData = Base64.getDecoder().decode(base64Text);
                log.info("Base64解码成功，二进制大小：{}字节", imageBinaryData.length);
            } catch (IllegalArgumentException e) {
                log.error("Base64解码失败：文本不是有效Base64格式", e);
                return Result.error("文件编码错误，请重新上传");
            }

            File saveDir = new File(header);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            String fileName = UUID.randomUUID().toString()
                    + originalFilename.substring(originalFilename.lastIndexOf("."));

            tempSavedFile = new File(saveDir, fileName); // 拼接完整路径

            try (FileOutputStream fos = new FileOutputStream(tempSavedFile)){
                fos.write(imageBinaryData); // 写入解码后的二进制数据
                fos.flush();
                try {
                    imageUploadDto.setImageName(imageName.substring(0,imageName.lastIndexOf(".")));
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
        }catch (IOException e) {
            log.error("文件保存异常", e);
            return Result.error("文件保存失败，请检查服务器磁盘");
        } catch (Exception e) {
            log.error("上传系统异常", e);
            return Result.error("系统异常，请重试");
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
            FindUserLikeDto findUserLikeDto = new FindUserLikeDto();
            findUserLikeDto.setImageId(classInfo.getImageId());
            findUserLikeDto.setUserId(UserHolder.getUserId());
            UserLike userLike = userLikeMapper.findUserLikeRecord(findUserLikeDto);
            if(userLike == null)
            {
                classInfo.setIsLike(0);
            }
            else
            {
                classInfo.setIsLike(1);
            }
            FindBookmarkDto findBookmarkDto = new FindBookmarkDto();
            findBookmarkDto.setImageId(classInfo.getImageId());
            findBookmarkDto.setUserId(UserHolder.getUserId());
            Bookmark bookmark = bookmarkMapper.findBookmarkRecord(findBookmarkDto);
            if(bookmark == null)
            {
                classInfo.setIsBookmark(0);
            }
            else
            {
                classInfo.setIsBookmark(1);
            }
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
            FindUserLikeDto findUserLikeDto = new FindUserLikeDto();
            findUserLikeDto.setImageId(classInfo.getImageId());
            findUserLikeDto.setUserId(UserHolder.getUserId());
            UserLike userLike = userLikeMapper.findUserLikeRecord(findUserLikeDto);
            if(userLike == null)
            {
                classInfo.setIsLike(0);
            }
            else
            {
                classInfo.setIsLike(1);
            }
            FindBookmarkDto findBookmarkDto = new FindBookmarkDto();
            findBookmarkDto.setImageId(classInfo.getImageId());
            findBookmarkDto.setUserId(UserHolder.getUserId());
            Bookmark bookmark = bookmarkMapper.findBookmarkRecord(findBookmarkDto);
            if(bookmark == null)
            {
                classInfo.setIsBookmark(0);
            }
            else
            {
                classInfo.setIsBookmark(1);
            }
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
            FindUserLikeDto findUserLikeDto = new FindUserLikeDto();
            findUserLikeDto.setImageId(classInfo.getImageId());
            findUserLikeDto.setUserId(UserHolder.getUserId());
            UserLike userLike = userLikeMapper.findUserLikeRecord(findUserLikeDto);
            if(userLike == null)
            {
                classInfo.setIsLike(0);
            }
            else
            {
                classInfo.setIsLike(1);
            }
            FindBookmarkDto findBookmarkDto = new FindBookmarkDto();
            findBookmarkDto.setImageId(classInfo.getImageId());
            findBookmarkDto.setUserId(UserHolder.getUserId());
            Bookmark bookmark = bookmarkMapper.findBookmarkRecord(findBookmarkDto);
            if(bookmark == null)
            {
                classInfo.setIsBookmark(0);
            }
            else
            {
                classInfo.setIsBookmark(1);
            }
            classInfo.setImageUrl(header1 + classInfo.getImageUrl());
            classInfo.setHeadshotUrl(header1 + classInfo.getHeadshotUrl());
        }
        return Result.success(dataList);
    }

    @Override
    public Result<T> deleteImage(int imageId)
    {
        try{
            Image userIsDeleted = imageMapper.getUserIdDeleted(imageId);
            if(userIsDeleted == null)
            {
                return Result.error("图片不存在");
            }
            int userIdDeleted = userIsDeleted.getImageOwner();

            int user = UserHolder.getUserId();
            if(userIdDeleted == user)
            {
                try{
                    int x = imageMapper.deleteImage(imageId);
                    if(x <= 0)
                    {
                        throw new RuntimeException("删除图片失败，请稍后再试");
                    }
                    else
                    {
                        return Result.success();
                    }
                }catch(Exception e){
                    throw new RuntimeException("系统异常，删除失败",e);
                }
            }
            else
            {
                return Result.error("无法删除他人图片");
            }
        }catch(Exception e){
            throw new RuntimeException("系统异常，删除失败",e);
        }
    }

    @Override
    public Result<GetImageInformationVo> getImageInformation(int imageId)
    {
        try{
            GetImageInformationVo getImageInformationVo = imageMapper.getImageInformation(imageId);
            if(getImageInformationVo == null)
            {
                return Result.error("获取图片信息失败或图片被删除");
            }
            int userId = UserHolder.getUserId();
            BrowsingHistory browsingHistory = browsingHistoryMapper.findBrowsingRecord(imageId,userId);
            InsertBrowsingHistoryDto insertBrowsingHistoryDto = new InsertBrowsingHistoryDto();
            insertBrowsingHistoryDto.setImageId(imageId);
            insertBrowsingHistoryDto.setUserId(userId);
            insertBrowsingHistoryDto.setBrowsingTime(String.valueOf(LocalDateTime.now()));
            if(browsingHistory == null)
            {
                browsingHistoryMapper.insertBrowsingRecord(insertBrowsingHistoryDto);
            }
            else
            {
                browsingHistoryMapper.updateBrowsingRecord(insertBrowsingHistoryDto);
            }
            FindUserLikeDto findUserLikeDto = new FindUserLikeDto();
            findUserLikeDto.setImageId(getImageInformationVo.getImageId());
            findUserLikeDto.setUserId(UserHolder.getUserId());
            UserLike userLike = userLikeMapper.findUserLikeRecord(findUserLikeDto);
            if(userLike == null)
            {
                getImageInformationVo.setIsLike(0);
            }
            else
            {
                getImageInformationVo.setIsLike(1);
            }
            FindBookmarkDto findBookmarkDto = new FindBookmarkDto();
            findBookmarkDto.setImageId(getImageInformationVo.getImageId());
            findBookmarkDto.setUserId(UserHolder.getUserId());
            Bookmark bookmark = bookmarkMapper.findBookmarkRecord(findBookmarkDto);
            if(bookmark == null)
            {
                getImageInformationVo.setIsBookmark(0);
            }
            else
            {
                getImageInformationVo.setIsBookmark(1);
            }
            return Result.success(getImageInformationVo);
        }catch(RuntimeException e) {
            throw new RuntimeException("获取图片信息失败,请稍后再试");
        }
    }
}
