package com.bdilab.sharedcalendar.service.pub;

import com.bdilab.sharedcalendar.model.User;

public interface PubService {
    User userSignIn(String rescode);
    boolean updateUserInfo(User user);
    boolean insertUser(User user);
    User getUserInfo(Integer userId);
}
