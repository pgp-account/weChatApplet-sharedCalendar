package com.bdilab.sharedcalendar.model;

import java.util.Date;

public class Event {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.fk_creator_id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Integer fkCreatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.event_name
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private String eventName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.start_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Date startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.end_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Date endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.fk_type_id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Integer fkTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.event_frequency
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private int eventFrequency;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.event_end_condition
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private int eventEndCondition;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.repeat_end_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Date repeatEndTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.repeat_times
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Integer repeatTimes;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.current_repeat_times
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private Integer currentRepeatTimes;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.notice_choice
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private int noticeChoice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column event.event_content
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    private String eventContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.id
     *
     * @return the value of event.id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.id
     *
     * @param id the value for event.id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.fk_creator_id
     *
     * @return the value of event.fk_creator_id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Integer getFkCreatorId() {
        return fkCreatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.fk_creator_id
     *
     * @param fkCreatorId the value for event.fk_creator_id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setFkCreatorId(Integer fkCreatorId) {
        this.fkCreatorId = fkCreatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.event_name
     *
     * @return the value of event.event_name
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.event_name
     *
     * @param eventName the value for event.event_name
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.start_time
     *
     * @return the value of event.start_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.start_time
     *
     * @param startTime the value for event.start_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.end_time
     *
     * @return the value of event.end_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.end_time
     *
     * @param endTime the value for event.end_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.fk_type_id
     *
     * @return the value of event.fk_type_id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Integer getFkTypeId() {
        return fkTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.fk_type_id
     *
     * @param fkTypeId the value for event.fk_type_id
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setFkTypeId(Integer fkTypeId) {
        this.fkTypeId = fkTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.event_frequency
     *
     * @return the value of event.event_frequency
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public int getEventFrequency() {
        return eventFrequency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.event_frequency
     *
     * @param eventFrequency the value for event.event_frequency
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setEventFrequency(int eventFrequency) {
        this.eventFrequency = eventFrequency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.event_end_condition
     *
     * @return the value of event.event_end_condition
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public int getEventEndCondition() {
        return eventEndCondition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.event_end_condition
     *
     * @param eventEndCondition the value for event.event_end_condition
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setEventEndCondition(int eventEndCondition) {
        this.eventEndCondition = eventEndCondition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.repeat_end_time
     *
     * @return the value of event.repeat_end_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Date getRepeatEndTime() {
        return repeatEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.repeat_end_time
     *
     * @param repeatEndTime the value for event.repeat_end_time
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setRepeatEndTime(Date repeatEndTime) {
        this.repeatEndTime = repeatEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.repeat_times
     *
     * @return the value of event.repeat_times
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Integer getRepeatTimes() {
        return repeatTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.repeat_times
     *
     * @param repeatTimes the value for event.repeat_times
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setRepeatTimes(Integer repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.current_repeat_times
     *
     * @return the value of event.current_repeat_times
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public Integer getCurrentRepeatTimes() {
        return currentRepeatTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.current_repeat_times
     *
     * @param currentRepeatTimes the value for event.current_repeat_times
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setCurrentRepeatTimes(Integer currentRepeatTimes) {
        this.currentRepeatTimes = currentRepeatTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.notice_choice
     *
     * @return the value of event.notice_choice
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public int getNoticeChoice() {
        return noticeChoice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.notice_choice
     *
     * @param noticeChoice the value for event.notice_choice
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setNoticeChoice(int noticeChoice) {
        this.noticeChoice = noticeChoice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column event.event_content
     *
     * @return the value of event.event_content
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public String getEventContent() {
        return eventContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column event.event_content
     *
     * @param eventContent the value for event.event_content
     *
     * @mbg.generated Tue Apr 16 14:22:15 CST 2019
     */
    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}