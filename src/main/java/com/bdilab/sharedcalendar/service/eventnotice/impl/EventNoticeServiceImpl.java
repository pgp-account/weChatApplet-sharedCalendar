package com.bdilab.sharedcalendar.service.eventnotice.impl;

import com.bdilab.sharedcalendar.mapper.EventNoticeMapper;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventNotice;
import com.bdilab.sharedcalendar.service.eventnotice.EventNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventNoticeServiceImpl implements EventNoticeService {
    @Autowired
    EventNoticeMapper eventNoticeMapper;
    /**
     * 创建日程提醒
     * @return
     */
    @Override
    public EventNotice createEventNotice(Integer typeId,Integer eventId,Integer userId, Date noticeTime) {
        EventNotice eventNotice = new EventNotice();
        eventNotice.setFkTypeId(typeId);
        eventNotice.setFkEventId(eventId);
        eventNotice.setFkUserId(userId);
        eventNotice.setNoticeTime(noticeTime);
        //若当前时间小于提醒时间，则设置未提醒0，否则设置已提醒1
        if (new Date().before(noticeTime)){
            eventNotice.setIsNoticed(0);
        }else {
            eventNotice.setIsNoticed(1);
        }
        if(eventNoticeMapper.insertEventNotice(eventNotice)==1) return eventNotice;
        return null;
    }

    @Override
    public EventNotice selectEventNoticeById(Integer noticeId) {
        return eventNoticeMapper.getEventNotice(noticeId);
    }

    /**
     * 获取用户的未提醒的提醒日程
     * @param userId
     * @return
     */
    @Override
    public List<EventNotice> getEventNoticeByUserId(Integer userId) {
        return eventNoticeMapper.selectEventNoticeByUserId(userId);
    }

    @Override
    public EventNotice selectEventNoticeByEventId(Integer eventId) {
        return eventNoticeMapper.selectEventNoticeByEventId(eventId);
    }

    /**
     * 修改日程的提醒状态为已提醒
     * @param noticeId
     * @return
     */
    @Override
    public boolean setNoticeRead(Integer noticeId) {
        if(eventNoticeMapper.setIsNotice(noticeId)==1) return true;
        return false;
    }

    /**
     * 重置日程提醒时间
     * @param noticeId
     * @param noticeTime
     * @return
     */
    @Override
    public boolean resetNoticeTime(Integer noticeId,Date noticeTime) {
        eventNoticeMapper.setNoticeTime(noticeId,noticeTime);
        return true;
    }

    /**
     * 删除日程提醒
     * @return
     */
    @Override
    public boolean deleteEventNoticeById(Integer noticeId) {
        eventNoticeMapper.deleteEventNotice(noticeId);
        return true;
    }
}
