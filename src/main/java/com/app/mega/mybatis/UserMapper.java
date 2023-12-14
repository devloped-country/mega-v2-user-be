package com.app.mega.mybatis;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void updateUser(String name, String phone, Long id);

    void updatePassword(String password, Long id);
}
