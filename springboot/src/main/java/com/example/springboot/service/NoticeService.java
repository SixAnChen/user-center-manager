package com.example.springboot.service;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.entity.Notice;
import com.example.springboot.entity.Params;
import com.example.springboot.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-03 01:06
 */
@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeDao;


    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeDao.insertSelective(notice);
    }

    public PageInfo<Notice> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Notice> list = noticeDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeDao.updateByPrimaryKeySelective(notice);
    }

    public void delete(Integer id) {
        noticeDao.deleteByPrimaryKey(id);
    }

    public List<Notice> findTop() {
        return noticeDao.findTop3();
    }
}
