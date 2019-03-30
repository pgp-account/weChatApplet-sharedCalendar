package com.bdilab.sharedcalendar.service.pub;

import com.bdilab.sharedcalendar.model.User;

public interface PubService {
    public User userSignCheck(String rescode);
    public boolean userSignUp(User user);
}
