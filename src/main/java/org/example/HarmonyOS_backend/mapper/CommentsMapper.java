package org.example.HarmonyOS_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.HarmonyOS_backend.model.dto.PostCommentsDto;
import org.example.HarmonyOS_backend.model.entity.Comments;

@Mapper
public interface CommentsMapper {
    @Insert("INSERT INTO comments(content,image_id,user_id,comments_time) VALUES (#{content},#{imageId},#{userId},#{commentsTime})")
    int postComments(PostCommentsDto postCommentsDto);

    @Update("UPDATE comments SET is_delete = 1 WHERE id = #{id}")
    int deleteComments(int id);

    @Select("SELECT * FROM comments WHERE id = #{id}")
    Comments getUserIdDeleted(int id);
}
