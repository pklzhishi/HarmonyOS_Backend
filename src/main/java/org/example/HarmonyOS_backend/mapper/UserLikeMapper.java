package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;
import org.example.HarmonyOS_backend.model.entity.UserLike;

public interface UserLikeMapper {
    @Select("SELECT * FROM user_like WHERE image_id = #{imageId} AND user_id = #{userId}")
    UserLike findUserLikeRecord(FindUserLikeDto findUserLikeDto);

    @Insert("INSERT INTO user_like(image_id,user_id) VALUES (#{imageId},#{userId})")
    int insertUserLikeRecord(FindUserLikeDto findUserLikeDto);

    @Delete("DELETE FROM user_like WHERE image_id = #{imageId} AND user_id = #{userId}")
    int deleteUserLikeRecord(FindUserLikeDto findUserLikeDto);

    @Update("UPDATE image SET image_like = image_like + 1 WHERE image_id = #{imageId}")
    int addLike(int imageId);

    @Update("UPDATE image SET image_like = image_like - 1 WHERE image_id = #{imageId}")
    int reduceLike(int imageId);
}
