package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.User;
import org.apache.ibatis.annotations.Param;

public interface PubMapper {

    User selectUserById(@Param("user_id")int user_id);
    User selectUserByOpenid(@Param("open_id")String open_id);
    User insertUser(@Param("user")User user);
    int updateUserInfo(@Param("user")User user);
}
