package cn.unuuc.novel.service.impl;

import cn.unuuc.novel.entity.Admin;
import cn.unuuc.novel.mapper.AdminMapper;
import cn.unuuc.novel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin getAdminByAccount(String account) {
        return adminMapper.getAdminByAccount(account);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.getAdminById(id);
    }
}
