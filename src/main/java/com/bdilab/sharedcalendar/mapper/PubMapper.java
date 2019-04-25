package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.User;
import org.apache.ibatis.annotations.Param;

public interface PubMapper {

    User selectUserById(int user_id);
    User selectUserByOpenid(String open_id);
    int insertUser(User user);

}
