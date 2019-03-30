package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.User;
import org.apache.ibatis.annotations.Param;

public interface PubMapper {
    /**
     * 根据openId搜索用户
     * @param openId
     * @return
     */
    User selectUserByOpenId(@Param("openId")String openId);
    User insertUser(@Param("user")User user);
}
