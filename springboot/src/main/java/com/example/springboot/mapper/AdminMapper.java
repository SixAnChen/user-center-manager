package com.example.springboot.mapper;

import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminMapper extends Mapper<Admin> {

    @Select("select * from admin where username = #{username} and password = #{password}")
    Admin login(@Param("username") String username, @Param("password") String password);

    @Select("select * from admin where username = #{username}")
    Admin selectByName(String username);

    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer valueOf);

}
