package com.example.springboot.service;

import com.example.springboot.entity.Log;
import com.example.springboot.entity.Params;
import com.example.springboot.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LogService {

    @Resource
    private LogMapper logDao;


    public void add(Log type) {
        logDao.insertSelective(type);
    }


    public List<Log> findBySearch(Params params) {
        return logDao.findBySearch(params);
    }

    public void delete(Integer id) {
        logDao.deleteByPrimaryKey(id);
    }

}
