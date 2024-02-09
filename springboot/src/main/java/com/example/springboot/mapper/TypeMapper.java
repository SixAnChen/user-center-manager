package com.example.springboot.mapper;


import com.example.springboot.entity.Params;
import com.example.springboot.entity.Type;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-02 09:00
 */
@Repository
public interface TypeMapper extends Mapper<Type> {

    /**
     * 查询所有分类信息
     */
    @Select("select * from type")
    List<Type> findAll();


    List<Type> findBySearch(@Param("params") Params params);

}
