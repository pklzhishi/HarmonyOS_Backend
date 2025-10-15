package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.HarmonyOS_backend.model.dto.PostCommentsDto;
import org.example.HarmonyOS_backend.model.entity.Comments;
import org.example.HarmonyOS_backend.model.vo.GetBrowsingHistoryListVo;
import org.example.HarmonyOS_backend.model.vo.GetCommentsListVo;

import java.util.List;

@Mapper
public interface CommentsMapper {
    @Insert("INSERT INTO comments(content,image_id,user_id,comments_time) VALUES (#{content},#{imageId},#{userId},#{commentsTime})")
    int postComments(PostCommentsDto postCommentsDto);

    @Update("UPDATE comments SET is_delete = 1 WHERE id = #{id}")
    int deleteComments(int id);

    @Select("SELECT * FROM comments WHERE id = #{id}")
    Comments getUserIdDeleted(int id);

    @Select("SELECT id,comments.user_id,content,comments_time,username,headshot_url FROM comments JOIN user ON comments.user_id = user.user_id " +
            "WHERE image_id = #{imageId} AND comments.is_delete = 0 ORDER BY comments_time ASC")
    List<GetCommentsListVo> getCommentsList(int imageId);
}
