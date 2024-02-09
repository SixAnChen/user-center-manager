package com.example.springboot.service;

import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import com.example.springboot.mapper.TypeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeService {

    @Resource
    TypeMapper typeMapper;

    public List<Type> findAll() {
        return typeMapper.findAll();
    }

    public void add(Type type) {
        typeMapper.insertSelective(type);
    }

    public PageInfo<Type> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Type> list = typeMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Type type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

    public void delete(Integer id) {
        typeMapper.deleteByPrimaryKey(id);
    }

}
