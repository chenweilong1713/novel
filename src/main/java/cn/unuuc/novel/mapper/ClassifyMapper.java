package cn.unuuc.novel.mapper;

import cn.unuuc.novel.entity.Classify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassifyMapper {

    @Insert("insert into classify values(default,#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Classify classify);

    @Select("select * from classify where id = #{id}")
    Classify getById(Integer id);

    @Select("select * from classify where name = #{name}")
    Classify getByName(String name);
}
