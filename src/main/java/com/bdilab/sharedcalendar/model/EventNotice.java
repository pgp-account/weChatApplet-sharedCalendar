package com.bdilab.sharedcalendar.model;

import java.util.Date;

public class EventNotice {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_notice.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_notice.fk_type_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_notice.fk_event_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkEventId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_notice.fk_user_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer fkUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_notice.notice_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Date noticeTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event_notice.is_noticed
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    private Integer isNoticed;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_notice.id
     *
     * @return the value of event_notice.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_notice.id
     *
     * @param id the value for event_notice.id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_notice.fk_type_id
     *
     * @return the value of event_notice.fk_type_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkTypeId() {
        return fkTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_notice.fk_type_id
     *
     * @param fkTypeId the value for event_notice.fk_type_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkTypeId(Integer fkTypeId) {
        this.fkTypeId = fkTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_notice.fk_event_id
     *
     * @return the value of event_notice.fk_event_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkEventId() {
        return fkEventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_notice.fk_event_id
     *
     * @param fkEventId the value for event_notice.fk_event_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkEventId(Integer fkEventId) {
        this.fkEventId = fkEventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_notice.fk_user_id
     *
     * @return the value of event_notice.fk_user_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getFkUserId() {
        return fkUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_notice.fk_user_id
     *
     * @param fkUserId the value for event_notice.fk_user_id
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_notice.notice_time
     *
     * @return the value of event_notice.notice_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Date getNoticeTime() {
        return noticeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_notice.notice_time
     *
     * @param noticeTime the value for event_notice.notice_time
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event_notice.is_noticed
     *
     * @return the value of event_notice.is_noticed
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public Integer getIsNoticed() {
        return isNoticed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event_notice.is_noticed
     *
     * @param isNoticed the value for event_notice.is_noticed
     *
     * @mbg.generated Sat Mar 30 15:00:05 CST 2019
     */
    public void setIsNoticed(Integer isNoticed) {
        this.isNoticed = isNoticed;
    }
}