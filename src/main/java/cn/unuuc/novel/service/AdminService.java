package cn.unuuc.novel.service;


import cn.unuuc.novel.entity.Admin;

public interface AdminService {

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    Admin getAdminByAccount(String account);

    /**
     * 更新密码，其它字段不更新
     * @param admin
     * @return
     */
    int updateAdmin(Admin admin);

    Admin getAdminById(Integer id);
}
