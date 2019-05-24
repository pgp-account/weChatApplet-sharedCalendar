package com.bdilab.sharedcalendar.model;

import java.util.Date;

public class NoticeForPush {
    String nameAndType;
    String openId;
    String description;
    Date date;
    Integer noticeId;

    public String getNameAndType() {
        return nameAndType;
    }

    public void setNameAndType(String nameAndType) {
        this.nameAndType = nameAndType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }
    public Integer getNoticeId(){
        return noticeId;
    }
}
