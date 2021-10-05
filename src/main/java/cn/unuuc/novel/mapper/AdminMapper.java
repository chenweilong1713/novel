package cn.unuuc.novel.mapper;

import cn.unuuc.novel.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {

    @Select("select * from admin where account = #{account}")
    Admin getAdminByAccount(String account);


    @Update("update admin set password = #{password} where id = #{id}")
    int update(Admin admin);

    @Select("select * from admin where id = #{id}")
    Admin getAdminById(Integer id);
}
