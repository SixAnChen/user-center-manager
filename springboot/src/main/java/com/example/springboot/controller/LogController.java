package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.entity.Log;
import com.example.springboot.entity.Params;
import com.example.springboot.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;


    @PostMapping
    public Result save(@RequestBody Log log) {
        logService.add(log);
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        return Result.success(logService.findBySearch(params));
    }


    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Log> list) {
        for (Log log : list) {
            logService.delete(log.getId());
        }
        return Result.success();
    }

}
