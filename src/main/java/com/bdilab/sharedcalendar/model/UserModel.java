package com.bdilab.sharedcalendar.model;

public class UserModel {

    /**
     * 用户id
     */
    private int user_id;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 微信后台生成的一张临时的身份证，有效时间为5分钟
     * 后台需要用该临时身份证去微信服务端获取微信用户身份id
     */
    private String rescode;

    /**
     * 微信用户的唯一标识
     */
    private String openid;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRescode() {
        return rescode;
    }
}

