package com.example.springboot.service;

import cn.hutool.crypto.SecureUtil;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.utils.JwtTokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-09-23 14:42
 */
@Service
public class AdminService {

    @Resource
    AdminMapper adminMapper;

    private static final String PASS_SALT = "chen";  //加盐
//    private static final String DEFAULT_PASS = "123456";  //默认密码

    public Admin login(Admin admin) {
        admin.setPassword(securePass(admin.getPassword()));
        // 查询数据库
        Admin db_admin = adminMapper.login(admin.getUsername(), admin.getPassword());
        if (db_admin == null) {
            throw new CustomException("用户名或密码错误");
        }
        // 生成jwt token给前端
        String token = JwtTokenUtils.genToken(db_admin.getId().toString(), db_admin.getPassword());
        db_admin.setToken(token);
        return db_admin;
    }

    public Admin register(Admin admin) {
        Admin dbAdmin = adminMapper.selectByName(admin.getUsername());
        if (dbAdmin != null) {
            // 抛出一个自定义的异常
            throw new CustomException("用户名已存在");
        }
        // md5 加盐、加密
        admin.setPassword(securePass(admin.getPassword()));
        adminMapper.insert(admin);
        return admin;
    }

    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }

    public Admin findById(Integer valueOf) {
        return adminMapper.selectById(valueOf);
    }


    public void insert(Admin admin) {
        adminMapper.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void updatePass(Admin admin) {

        String oldPassword = securePass(admin.getOldPassword());

        String password = admin.getPassword();
        if (!oldPassword.equals(admin.getPassword())) {
            throw new CustomException("旧密码错误！");
        }
        // 设置新密码，并完成加密
        admin.setPassword(securePass(admin.getNewPassword()));
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void resetPass(Admin admin) {
        Admin dbAdmin = adminMapper.selectByName(admin.getUsername());
        if (dbAdmin == null) {
            throw new CustomException("用户不存在");
        }
        if (!admin.getPhone().equals(dbAdmin.getPhone())) {
            throw new CustomException("验证错误");
        }
        dbAdmin.setPassword(securePass(admin.getNewPassword()));
        adminMapper.updateByPrimaryKeySelective(dbAdmin);
    }
}
