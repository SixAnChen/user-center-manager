package com.example.springboot.mapper;

import com.example.springboot.entity.Audit;
import com.example.springboot.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface AuditMapper extends Mapper<Audit> {

    List<Audit> findBySearch(@Param("params") Params params);
}

