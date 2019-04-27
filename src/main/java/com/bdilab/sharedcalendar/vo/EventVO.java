package com.bdilab.sharedcalendar.vo;

import com.bdilab.sharedcalendar.model.Event;

import java.util.Date;

/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/24
 * Time:19:09
 */
public class EventVO {
    /**
     * 日程id
     */
    private Integer id;

    /**
     * 日程名称
     */
    private String eventName;

    private Integer fkTypeId;

    /**
     * 日程内容
     */
    private String eventContent;

    /**
     * 日程开始时间
     */
    private Date startTime;

    private Date endTime;

    /**
     * 日程频率:0-不重复；1-每日；2-每周；3-每年
     */
    private Integer eventFrequency;


    /**
     * 日程重复终止条件:0-重复次数；1-重复截止时间；2-无限重复
     *
     */
    private Integer eventEndCondition;

    /**
     * 日程终止时间
     */
    private Date repeatEndTime;

    /**
     * 日程总重复次数
     */
    private Integer repeatTimes;

    private Integer noticeChoice;
    /**
     * 当前重复次数
     */
    private Integer currentRepeatTimes;

    /**
     * 提醒时间
     */
    private Date noticeTime;

    /**
     * 日程创建人昵称
     */
    private String creatorName;

    /**
     * 日程类型名
     */
    private String eventTypeName;

    public EventVO(Event event){
        this.id = event.getId();
        this.eventName = event.getEventName();
        this.eventContent = event.getEventContent();
        this.eventEndCondition = event.getEventEndCondition();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.repeatEndTime = event.getRepeatEndTime();
        this.currentRepeatTimes = event.getCurrentRepeatTimes();
        this.repeatTimes = event.getRepeatTimes();
        this.eventFrequency = event.getEventFrequency();
        this.fkTypeId = event.getFkTypeId();
        this.noticeChoice = event.getNoticeChoice();
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public void setEventContent(String eventContent){
        this.eventContent=eventContent;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCurrentRepeatTimes(Integer currentRepeatTimes) {
        this.currentRepeatTimes = currentRepeatTimes;
    }

    public void setEventEndCondition(Integer eventEndCondition) {
        this.eventEndCondition = eventEndCondition;
    }

    public void setEventFrequency(Integer eventFrequency) {
        this.eventFrequency = eventFrequency;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public void setRepeatEndTime(Date repeatEndTime) {
        this.repeatEndTime = repeatEndTime;
    }

    public void setRepeatTimes(Integer repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public void setFkTypeId(Integer fkTypeId) {
        this.fkTypeId = fkTypeId;
    }

    public void setNoticeChoice(Integer noticeChoice) {
        this.noticeChoice = noticeChoice;
    }

    public Integer getNoticeChoice() {
        return noticeChoice;
    }

    public Integer getFkTypeId() {
        return fkTypeId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getId() {
        return id;
    }

    public String getEventName(){
        return eventName;
    }

    public String getEventContent() {
        return eventContent;
    }

    public Integer getEventFrequency() {
        return eventFrequency;
    }

    public Date getRepeatEndTime() {
        return repeatEndTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Integer getCurrentRepeatTimes() {
        return currentRepeatTimes;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public Integer getRepeatTimes() {
        return repeatTimes;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public int getEventEndCondition() {
        return eventEndCondition;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

}
