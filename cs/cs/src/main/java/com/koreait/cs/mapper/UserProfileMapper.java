package com.koreait.cs.mapper;

import com.koreait.cs.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserProfileMapper {
    @Select("select * from user where email=#{email}")
    User getUserProfile(@Param("email") String email);
}

