package com.bdilab.sharedcalendar.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.avatar_url
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.nick_name
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    private String nickName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_openid
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    private String userOpenid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.avatar_url
     *
     * @return the value of user.avatar_url
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.avatar_url
     *
     * @param avatarUrl the value for user.avatar_url
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.nick_name
     *
     * @return the value of user.nick_name
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.nick_name
     *
     * @param nickName the value for user.nick_name
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_openid
     *
     * @return the value of user.user_openid
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public String getUserOpenid() {
        return userOpenid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_openid
     *
     * @param userOpenid the value for user.user_openid
     *
     * @mbg.generated Mon Apr 01 22:35:05 CST 2019
     */
    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid;
    }
}