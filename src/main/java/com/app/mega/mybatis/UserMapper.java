package com.app.mega.mybatis;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void updateUser(String name, String password, String phone, Long id);
}
