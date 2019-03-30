package com.bdilab.sharedcalendar.model;

import java.util.Date;

public class SubscribedEventType {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subscribed_event_type.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subscribed_event_type.fk_type_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subscribed_event_type.fk_user_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subscribed_event_type.fk_creator_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkCreatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subscribed_event_type.subscribe_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Date subscribeTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subscribed_event_type.id
     *
     * @return the value of subscribed_event_type.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subscribed_event_type.id
     *
     * @param id the value for subscribed_event_type.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subscribed_event_type.fk_type_id
     *
     * @return the value of subscribed_event_type.fk_type_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkTypeId() {
        return fkTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subscribed_event_type.fk_type_id
     *
     * @param fkTypeId the value for subscribed_event_type.fk_type_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkTypeId(Integer fkTypeId) {
        this.fkTypeId = fkTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subscribed_event_type.fk_user_id
     *
     * @return the value of subscribed_event_type.fk_user_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkUserId() {
        return fkUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subscribed_event_type.fk_user_id
     *
     * @param fkUserId the value for subscribed_event_type.fk_user_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subscribed_event_type.fk_creator_id
     *
     * @return the value of subscribed_event_type.fk_creator_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkCreatorId() {
        return fkCreatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subscribed_event_type.fk_creator_id
     *
     * @param fkCreatorId the value for subscribed_event_type.fk_creator_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkCreatorId(Integer fkCreatorId) {
        this.fkCreatorId = fkCreatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subscribed_event_type.subscribe_time
     *
     * @return the value of subscribed_event_type.subscribe_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Date getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subscribed_event_type.subscribe_time
     *
     * @param subscribeTime the value for subscribed_event_type.subscribe_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
}