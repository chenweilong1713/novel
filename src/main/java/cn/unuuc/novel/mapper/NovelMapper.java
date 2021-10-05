package cn.unuuc.novel.mapper;

import cn.unuuc.novel.entity.Novel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NovelMapper {

    /**
     * @param novel
     * @return
     */
    @Insert("insert into novel values(default,#{title},#{author},#{indexUrl},#{coverImg},#{preface},#{cId},#{rId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Novel novel);

    @Select("select * from novel where title = #{title}")
    Novel getByName(String title);

    @Select("select count(id) from novel")
    int getCountAll();


    @Select("select id,title,author,index_url,(select name from classify where classify.id = c_id) as class_name,r_id  from novel limit #{offset},#{limit}")
    List<Novel> selectPage(Integer offset, Integer limit);
}
