package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.User;
import org.apache.ibatis.annotations.Param;

public interface PubMapper {
    /**
     * 根据openId搜索用户
     * @param user_id
     * @return
     */
    User selectUserById(@Param("user_id")int user_id);
    User selectUserByOpenid(@Param("open_id")String open_id);
    User insertUser(@Param("user")User user);
    int updateUserInfo(@Param("user")User user);
}
