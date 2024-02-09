package com.example.springboot.service;

import com.example.springboot.entity.Params;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：用户服务类
 * 作者：SixAn
 * 日期：2023-09-21 14:46
 */
@Service
public class UserService {

    @Resource
    UserMapper userMapper;


    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }

    public PageInfo<User> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        return PageInfo.of(userMapper.findBySearch(params));
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }
}
