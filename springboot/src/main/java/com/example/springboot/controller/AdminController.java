package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.AutoLog;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-09-23 14:42
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    AdminService adminService;


    /**
     * 登录
     */
    @PostMapping("/login")
    @AutoLog("进行登录")
    public Result login(@RequestBody Admin admin) {
        return Result.success(adminService.login(admin));
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    @AutoLog("进行注册")
    public Result register(@RequestBody Admin admin) {
        return Result.success(adminService.register(admin));
    }


    /**
     * 新增、修改
     */
    @PostMapping
    @AutoLog("更新数据")
    public Result save(@RequestBody Admin admin) {
        if (admin.getId() == null) {
            adminService.insert(admin);
        } else {
            adminService.update(admin);
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePass")
    @AutoLog("修改密码")
    public Result updatePass(@RequestBody Admin admin) {
        adminService.updatePass(admin);
        return Result.success();
    }

    @AuthAccess
    @PutMapping("/resetPass")
    @AutoLog("重置密码")
    public Result resetPass(@RequestBody Admin admin) {
        if (StrUtil.isBlank(admin.getUsername()) || StrUtil.isBlank(admin.getPhone())) {
            return Result.error("数据输入不合法");
        }
        adminService.resetPass(admin);
        return Result.success();
    }
}
