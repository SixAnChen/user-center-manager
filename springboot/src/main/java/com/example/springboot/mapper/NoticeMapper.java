package com.example.springboot.mapper;

import com.example.springboot.entity.Notice;
import com.example.springboot.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-03 01:05
 */
@Repository
public interface NoticeMapper extends Mapper<Notice> {

    List<Notice> findBySearch(@Param("params") Params params);

    @Select("select * from notice where status = 1 order by time desc limit 3")
    List<Notice> findTop3();
}
