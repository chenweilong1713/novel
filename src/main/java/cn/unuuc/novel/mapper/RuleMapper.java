package cn.unuuc.novel.mapper;

import cn.unuuc.novel.entity.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RuleMapper {

    @Select("select * from rule where id = #{id}")
    Rule getById(Integer id);

    @Select("select * from rule")
    List<Rule> getAll();

    @Select("select count(id) from rule")
    int countAll();
}
