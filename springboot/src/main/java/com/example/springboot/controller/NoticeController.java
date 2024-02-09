package com.example.springboot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.AutoLog;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Notice;
import com.example.springboot.entity.Params;
import com.example.springboot.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-03 01:08
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping
    @AutoLog("更新系统公告")
    public Result save(@RequestBody Notice notice) {
        if (ObjectUtil.isEmpty(notice.getId())) {
            noticeService.add(notice);
        } else {
            noticeService.update(notice);
        }
        return Result.success();
    }

    @GetMapping
    @AutoLog("获取系统top3公告记录")
    public Result findTop() {
        List<Notice> list = noticeService.findTop();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("条件查询系统公告")
    public Result findBySearch(Params params) {
        PageInfo<Notice> info = noticeService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    @AutoLog("根据id删除系统公告")
    public Result delete(@PathVariable Integer id) {
        noticeService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Notice> list) {
        for (Notice notice : list) {
            noticeService.delete(notice.getId());
        }
        return Result.success();
    }
}
