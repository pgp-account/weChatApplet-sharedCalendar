package com.bdilab.sharedcalendar.service.eventnotice.impl;

import com.bdilab.sharedcalendar.mapper.EventInfoMapper;
import com.bdilab.sharedcalendar.mapper.EventNoticeMapper;
import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.mapper.PubMapper;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventNotice;
import com.bdilab.sharedcalendar.model.NoticeForPush;
import com.bdilab.sharedcalendar.service.eventnotice.EventNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventNoticeServiceImpl implements EventNoticeService {
    @Autowired
    EventNoticeMapper eventNoticeMapper;

    @Autowired
    EventTypeMapper eventTypeMapper;

    @Autowired
    EventInfoMapper eventInfoMapper;

    @Autowired
    PubMapper pubMapper;
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

    @Override
    public List<NoticeForPush> getUnreadNoticeByDate(Date date){
        Date datePlusOneMin = new Date(date.getTime()+60000);
        Date dateMinusOneMin = new Date(date.getTime()-60000);
        //查询这一时刻±1min内的提醒
        List<EventNotice> eventNotices =eventNoticeMapper.selectUnreadNoticeByDate(date,datePlusOneMin);
        List<NoticeForPush> wechatNotices = new ArrayList<>();
        for (EventNotice eventNotice:eventNotices
        ) {
            NoticeForPush wechatNotice = new NoticeForPush();
            wechatNotice.setDate(eventNotice.getNoticeTime());
            wechatNotice.setNoticeId(eventNotice.getId());
            wechatNotice.setNameAndType(eventTypeMapper.selectEventTypeById(eventNotice.getFkTypeId()).getTypeName()+"-"+eventInfoMapper.selectEventById(eventNotice.getFkEventId()).getEventName());
            wechatNotice.setOpenId(pubMapper.selectUserById(eventNotice.getFkUserId()).getUserOpenid());
            wechatNotice.setDescription("您的日程即将开始");
            wechatNotices.add(wechatNotice);
        }
        return wechatNotices;
    }

    /**
     * eventFrequency 0-不重复；1-每日；2-每周；3-每年
     * event_end_condition 0-重复次数；1-重复截止时间；2-无限重复
     * notice_choice 0-不设置提醒；1-提前十分钟；2-提前半小时
     */
    @Override
    public void setNoticeRead(int noticeId){
        //设为已提醒
        eventNoticeMapper.setIsNotice(noticeId);
        //根据条件创建新的提醒
        EventNotice eventNotice = eventNoticeMapper.getEventNotice(noticeId);
        Event event = eventInfoMapper.selectEventById(eventNotice.getFkEventId());

        //每日重复日程
        long timeInterval = 24 * 60 * 60 * 1000;
        //每周重复日程
        if(event.getEventFrequency()==2) timeInterval = 24 * 60 * 60 * 1000 * 7;

        long timeBeforeEvent;
        //提醒应提前多久
        switch (event.getNoticeChoice()){
            case 1:timeBeforeEvent = 10*60*1000;
                break;
            case 2:timeBeforeEvent = 30*60*1000;
                break;
            default:timeBeforeEvent=0;
        }
        //判断日程是否已经结束
        if(event.getEventFrequency()!=0){
            //重复次数
            if(event.getEventEndCondition()==0){
                Date nextTime = getNextTime(event,eventNotice);
                //每年重复
                if(event.getEventFrequency()==3){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(nextTime);
                    int nextYear =calendar.get(Calendar.YEAR);
                    calendar.setTime(event.getStartTime());
                    int firstYear =calendar.get(Calendar.YEAR);
                    int curRepeatTimes = nextYear - firstYear;
                    if(event.getRepeatTimes()>curRepeatTimes) eventNotice.setNoticeTime(nextTime);
                    else eventNotice.setNoticeTime(null);
                }
                //每周或每天重复
                else{
                    long repeatedTimes = (nextTime.getTime()+timeBeforeEvent-event.getStartTime().getTime())/(timeInterval)+1;
                    if(repeatedTimes<event.getRepeatTimes())    eventNotice.setNoticeTime(nextTime);
                    else eventNotice.setNoticeTime(null);
                }

            }
            //截止时间
            if(event.getEventEndCondition()==1){
                Date nextTime = getNextTime(event,eventNotice);
                if(nextTime.getTime()+timeBeforeEvent<=event.getRepeatEndTime().getTime())  eventNotice.setNoticeTime(nextTime);
                else eventNotice.setNoticeTime(null);
            }
        }
        //无限重复
        if(event.getEventEndCondition()==2){
            eventNotice.setNoticeTime(getNextTime(event,eventNotice));
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" Set Notice Read");
        eventNotice.setIsNoticed(1);
        if(eventNotice.getNoticeTime()!=null) eventNoticeMapper.insertEventNotice(eventNotice);
    }

    Date getNextYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,1);
        return calendar.getTime();
    }
    Date getNextTime(Event event,EventNotice eventNotice){
        Date nextNoticeTime;
        switch (event.getEventFrequency()){
            case 1:
                nextNoticeTime = new Date(eventNotice.getNoticeTime().getTime()+24*60*60*1000);
                break;
            case 2:
                nextNoticeTime = new Date(eventNotice.getNoticeTime().getTime()+7*24*60*60*1000);
                break;
            case 3:
                nextNoticeTime = getNextYear(eventNotice.getNoticeTime());
                break;
            default:nextNoticeTime=new Date();
        }
        return nextNoticeTime;
    }
}
