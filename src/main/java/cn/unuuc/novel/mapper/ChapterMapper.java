package cn.unuuc.novel.mapper;

import cn.unuuc.novel.entity.Chapter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ChapterMapper {

    @Insert("insert into chapter values(default,#{title},#{content},#{nId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Chapter chapter);

    /**
     * 根据小说外键查询章节数量
     * @param nId
     * @return
     */
    @Select("select count(id) from chapter where n_id = #{nId}")
    int countByNId(Integer nId);

    @Select("select count(id) from chapter where title = #{title} and n_id = #{nId}")
    int countByTitleAndNId(String title,Integer nId);

    @Select("select count(id) from chapter")
    int countAll();
}
