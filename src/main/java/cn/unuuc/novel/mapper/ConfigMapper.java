package cn.unuuc.novel.mapper;

import cn.unuuc.novel.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ConfigMapper {

    @Select("select * from config where id = #{id}")
    Config getById(Integer id);

    @Update("update config set fail_time = #{failTime},delay = #{delay},fail_delay = #{failDelay},url_thread_flag = #{urlThreadFlag},novel_thread_flag = #{novelThreadFlag}  where id = #{id}")
    int update(Config config);
}
