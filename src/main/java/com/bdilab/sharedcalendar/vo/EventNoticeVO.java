package com.bdilab.sharedcalendar.vo;

import com.bdilab.sharedcalendar.model.EventNotice;

import java.util.Date;

public class EventNoticeVO {
    private Integer id;
    private Integer fkTypeId;
    private Integer fkEventId;
    private Integer fkUserId;
    //日程名称
    private String eventName;
    //日程内容
    private String eventContent;
    //提醒时间
    private Date noticeTime;
    private Integer isNoticed;

    public EventNoticeVO(EventNotice eventNotice){
        this.id = eventNotice.getId();
        this.fkEventId = eventNotice.getFkEventId();
        this.fkTypeId = eventNotice.getFkTypeId();
        this.fkUserId = eventNotice.getFkUserId();
        this.noticeTime = eventNotice.getNoticeTime();
        this.isNoticed = eventNotice.getIsNoticed();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkTypeId() {
        return fkTypeId;
    }

    public void setFkTypeId(Integer fkTypeId) {
        this.fkTypeId = fkTypeId;
    }

    public Integer getFkEventId() {
        return fkEventId;
    }

    public void setFkEventId(Integer fkEventId) {
        this.fkEventId = fkEventId;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public Integer getIsNoticed() {
        return isNoticed;
    }

    public void setIsNoticed(Integer isNoticed) {
        this.isNoticed = isNoticed;
    }
}
