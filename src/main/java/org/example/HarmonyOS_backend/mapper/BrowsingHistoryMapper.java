package org.example.HarmonyOS_backend.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.HarmonyOS_backend.model.dto.InsertBrowsingHistoryDto;
import org.example.HarmonyOS_backend.model.entity.BrowsingHistory;
import org.example.HarmonyOS_backend.model.vo.GetBookmarkVo;
import org.example.HarmonyOS_backend.model.vo.GetBrowsingHistoryListVo;

import java.util.List;

@Mapper
public interface BrowsingHistoryMapper {
    @Select("SELECT * FROM browsing_history WHERE image_id = #{imageId} AND user_id = #{userId}")
    BrowsingHistory findBrowsingRecord(int imageId,int userId);

    @Insert("INSERT INTO browsing_history(image_id,user_id,browsing_time) VALUES (#{imageId},#{userId},#{browsingTime})")
    void insertBrowsingRecord(InsertBrowsingHistoryDto insertBrowsingHistoryDto);

    @Update("UPDATE browsing_history SET browsing_time = #{browsingTime} WHERE image_id = #{imageId} AND user_id = #{userId}")
    void updateBrowsingRecord(InsertBrowsingHistoryDto insertBrowsingHistoryDto);

    @Select("SELECT image.image_id,image_name,image_owner,image_url,image_like,image_bookmark,image_time,content,username,headshot_url " +
            "FROM user JOIN image ON user.user_id = image.image_owner " +
            "JOIN browsing_history ON image.image_id = browsing_history.image_id " +
            "WHERE user.is_delete = 0 AND image.is_delete = 0 AND browsing_history.is_delete = 0 AND browsing_history.user_id = #{userId} " +
            "ORDER BY browsing_time DESC")
    List<GetBrowsingHistoryListVo> getBrowsingHistoryList(int userId);
}
