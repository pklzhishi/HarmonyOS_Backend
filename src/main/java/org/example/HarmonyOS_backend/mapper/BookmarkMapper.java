package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.*;
import org.example.HarmonyOS_backend.model.dto.FindBookmarkDto;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;
import org.example.HarmonyOS_backend.model.entity.UserLike;

@Mapper
public interface BookmarkMapper {
    @Select("SELECT * FROM bookmark WHERE image_id = #{imageId} AND user_id = #{userId}")
    UserLike findBookmarkRecord(FindBookmarkDto findBookmarkDto);

    @Insert("INSERT INTO bookmark(image_id,user_id) VALUES (#{imageId},#{userId})")
    int insertBookmarkRecord(FindBookmarkDto findBookmarkDto);

    @Delete("DELETE FROM bookmark WHERE image_id = #{imageId} AND user_id = #{userId}")
    int deleteBookmarkRecord(FindBookmarkDto findBookmarkDto);

    @Update("UPDATE image SET image_bookmark = image_bookmark + 1 WHERE image_id = #{imageId}")
    int addBookmark(int imageId);

    @Update("UPDATE image SET image_bookmark = image_bookmark - 1 WHERE image_id = #{imageId}")
    int reduceBookmark(int imageId);
}
