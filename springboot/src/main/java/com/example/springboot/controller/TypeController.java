package com.example.springboot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.AutoLog;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import com.example.springboot.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-02 09:02
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    TypeService typeService;

    @GetMapping
    @AutoLog("获取所有用户角色")
    public Result findAll() {
        return Result.success(typeService.findAll());
    }

    /**
     * 条件查询角色信息
     */
    @GetMapping("/search")
    @AutoLog("条件查询角色信息")
    public Result findBySearch(Params params) {
        PageInfo<Type> info = typeService.findBySearch(params);
        return Result.success(info);
    }


    /**
     * 更新角色信息
     */
    @PostMapping
    @AutoLog("更新角色信息")
    public Result save(@RequestBody Type type) {
        if (ObjectUtil.isEmpty(type.getId())) {
            typeService.add(type);
        } else {
            typeService.update(type);
        }
        return Result.success();
    }


    /**
     * 根据id删除角色信息
     */
    @DeleteMapping("/{id}")
    @AutoLog("根据id删除角色信息")
    public Result delete(@PathVariable Integer id) {
        typeService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Type> list) {
        for (Type type : list) {
            typeService.delete(type.getId());
        }
        return Result.success();
    }
}
