package com.example.springboot.mapper;


import com.example.springboot.entity.Params;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {


    /**
     * 新增用户信息
     *
     * @param user
     */
    void insert(User user);


    /**
     * 修改用户信息
     *
     * @param user
     */
    void update(User user);


    /**
     * 删除用户信息
     *
     * @param id
     */
    void delete(Integer id);


    /**
     * @param params
     * @return
     */
    List<User> findBySearch(@Param("params") Params params);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Select("select user.*, type.name AS typeName from user left join type on user.typeId = type.id")
    List<User> findAll();
}
