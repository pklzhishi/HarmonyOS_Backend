package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.*;
import org.example.HarmonyOS_backend.model.dto.FindBookmarkDto;
import org.example.HarmonyOS_backend.model.dto.FindUserLikeDto;
import org.example.HarmonyOS_backend.model.entity.Bookmark;
import org.example.HarmonyOS_backend.model.entity.UserLike;
import org.example.HarmonyOS_backend.model.vo.GetBookmarkVo;

import java.util.List;

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

    @Select("SELECT image.image_id,image_name,image_owner,image_url,image_like,image_bookmark,content,image_time,username,headshot_url " +
            "FROM user JOIN image ON user.user_id = image.image_owner JOIN bookmark ON image.image_id = bookmark.image_id " +
            "WHERE user.is_delete = 0 AND image.is_delete = 0 AND bookmark.user_id = #{userId}")
    List<GetBookmarkVo> getBookmarkList(int userId);
}
