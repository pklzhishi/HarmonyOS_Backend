package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.*;
import org.example.HarmonyOS_backend.model.dto.ImageUploadDto;
import org.example.HarmonyOS_backend.model.entity.Comments;
import org.example.HarmonyOS_backend.model.entity.Image;
import org.example.HarmonyOS_backend.model.vo.GetImageRandomlyVo;
import org.example.HarmonyOS_backend.model.vo.MyImageVo;

import java.util.List;

@Mapper
public interface ImageMapper {
    @Insert("INSERT INTO image(image_name,image_owner,image_url,image_time,content) VALUES (#{imageName},#{imageOwner},#{imageUrl},#{imageTime},#{content})")
    int insertImage(ImageUploadDto imageUploadDto);

    @Select("SELECT image_id,image_name,image_url,image_like,image_time,content FROM image WHERE image_owner = #{imageOwner} AND is_delete = 0")
    List<MyImageVo> getMyImage(int imageOwner);

    @Select("SELECT COUNT(*) FROM image WHERE image_owner = #{imageOwner} AND is_delete = 0")
    int getMyImageNumber(int imageOwner);

    @Select("SELECT COUNT(*) " +
            "FROM user JOIN image ON user.user_id = image.image_owner WHERE user.is_delete = 0 AND image.is_delete = 0")
    int getAllImageNumber();

    @Select("SELECT image_id,image_name,image_owner,image_url,image_like,image_time,content,username,headshot_url " +
    "FROM user JOIN image ON user.user_id = image.image_owner WHERE user.is_delete = 0 AND image.is_delete = 0 " +
    "ORDER BY RAND() LIMIT #{count}")
    List<GetImageRandomlyVo> getImageRandomly(int count);

    @Select("SELECT image_id,image_name,image_owner,image_url,image_like,image_time,content,username,headshot_url " +
            "FROM user JOIN image ON user.user_id = image.image_owner WHERE user.is_delete = 0 AND image.is_delete = 0 " +
            "AND image_name LIKE CONCAT('%',#{imageName},'%') ORDER BY image_like DESC")
    List<GetImageRandomlyVo> searchImage(String imageName);

    @Update("UPDATE image SET is_delete = 1 WHERE image_id = #{imageId}")
    int deleteImage(int imageId);

    @Select("SELECT * FROM image WHERE image_id = #{imageId} AND is_delete = 0")
    Image getUserIdDeleted(int imageId);
}
