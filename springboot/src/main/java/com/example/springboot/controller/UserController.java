package com.example.springboot.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-09-21 14:46
 */

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getId() == null) {
            userService.insertUser(user);
        } else {
            userService.updateUser(user);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    @GetMapping("search")
    private Result findBySearch(Params params) {
        PageInfo<User> info = userService.findBySearch(params);
        return Result.success(info);
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<User> list) {
        for (User user : list) {
            userService.deleteUser(user.getId());
        }
        return Result.success();
    }

    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {
        // 思考：
        // 要一行一行的组装数据，塞到一个list里面
        // 每一行数据，其实就对应数据库表中的一行数据，也就是对应Java的一个实体类Type
        // 我们怎么知道它某一列就是对应某个表头呢？？ 需要映射数据，我们需要一个Map<key,value>，把这个map塞到list里

        // 1. 从数据库中查询出所有数据
        List<User> list = userService.findAll();

        // 抛出异常
        if (CollectionUtil.isEmpty(list)) {
            throw new ServerException("未找到数据");
        }

        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> tableList = new ArrayList<>(list.size());

        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for (User user : list) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("ID", user.getId());
            row.put("头像", user.getAvatar());
            row.put("姓名", user.getUsername());
            row.put("地址", user.getAddress());
            row.put("号码", user.getPhone());
            row.put("邮箱", user.getEmail());
            tableList.add(row);
        }

        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(tableList, true);

        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=type.xlsx");
        ServletOutputStream out = response.getOutputStream();
        wr.flush(out, true);
        wr.close();
        IoUtil.close(System.out);
        return Result.success();
    }

    @PostMapping("/upload")
    @AuthAccess
    public Result upload(MultipartFile file) throws IOException {
        List<User> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(User.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            for (User user : infoList) {
                try {
                    userService.insertUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }


    @GetMapping("/echarts/pie")
    public Result bie() {
        // 获取所有的用户数据
        List<User> userList = userService.findAll();

        Map<String, Long> collect = userList.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getTypeName()))
                .collect(Collectors.groupingBy(User::getTypeName, Collectors.counting()));

        // 最后返回给前端的数据结构
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(collect)) {
            for (String key : collect.keySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", key);
                map.put("value", collect.get(key));
                mapList.add(map);
            }
        }
        return Result.success(mapList);
    }

    @GetMapping("/echarts/bar")
    public Result bar() {
        // 查询出所有图书
        List<User> list = userService.findAll();
        Map<String, Long> collect = list.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getTypeName()))
                .collect(Collectors.groupingBy(User::getTypeName, Collectors.counting()));

        List<String> xAxis = new ArrayList<>();
        List<Long> yAxis = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(collect)) {
            for (String key : collect.keySet()) {
                xAxis.add(key);
                yAxis.add(collect.get(key));
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", xAxis);
        map.put("yAxis", yAxis);

        return Result.success(map);
    }
}
