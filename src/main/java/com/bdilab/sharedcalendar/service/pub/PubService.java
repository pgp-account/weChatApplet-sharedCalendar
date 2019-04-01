package com.bdilab.sharedcalendar.service.pub;

import com.bdilab.sharedcalendar.model.User;

public interface PubService {
    public User userSignIn(String rescode);
    public boolean updateUserInfo(User user);
}
