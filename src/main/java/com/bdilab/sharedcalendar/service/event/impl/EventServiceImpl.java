package com.bdilab.sharedcalendar.service.event.impl;

import com.bdilab.sharedcalendar.mapper.EventInfoMapper;
import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.service.event.EventService;
import com.bdilab.sharedcalendar.vo.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventInfoMapper eventInfoMapper;

    @Autowired
    private EventTypeMapper eventTypeMapper;
    /**
     * 创建日程
     */
    @Override
    public boolean createEvent(Event event) {
        boolean result = eventInfoMapper.selectEventById(event.getId()) == null;
        if(result){
            eventInfoMapper.insertEvent(event);
            return true;
        }
        return false;
    }

    /**
     * 根据日程id查找日程
     * @param event_id
     * @return
     */
    @Override
    public Event selectEventById(Integer event_id) {
        return null;
    }

    /**
     * 根据日程开始时间搜索日程
     * @param start_time
     * @return
     */
    @Override
    public List<Event> searchEventsByDate(String start_time) {
        //此处start_time的格式为"yyyyMMdd"，如“20190331”
        List<Event> events = eventInfoMapper.searchEventsByDate(start_time);
        return events;
    }

    /**
     * 更新日程
     * @param event
     * @return
     */
    @Override
    public boolean upadateEvent(Event event) {
        boolean result = eventInfoMapper.selectEventById(event.getId()) != null;
        if (result){
            eventInfoMapper.updateEvent(event);
            return true;
        }
        return false;
    }

    /**
     * 删除日程
     * @param event_id
     * @return
     */
    @Override
    public boolean deleteEvent(int event_id) {
        boolean result = eventInfoMapper.selectEventById(event_id) != null;
        if (result){
            eventInfoMapper.deleteEvent(event_id);
            return true;
        }
        return false;
    }

    @Override
    public List<EventVO> getEventVOsByDate(Date startTime, Date endTime, int userId){
        List<Event> events = eventInfoMapper.selectEventByUserId(userId);
        List<EventVO> eventVOs =  new ArrayList<>();
        for (Event event:events) {
            List<Date> repeatStartTimeList = repeatStartTime(startTime,endTime,event);

            for(Date date:repeatStartTimeList){
                EventVO eventVO = new EventVO(event);
                long duration = event.getEndTime().getTime()-event.getStartTime().getTime();
                eventVO.setCreatorName(eventTypeMapper.selectCreatorNameByTypeId(event.getFkTypeId()));
                eventVO.setEventTypeName(eventTypeMapper.selectEventTypeById(event.getFkTypeId()).getTypeName());
                int noticeChoice = eventInfoMapper.selectEventById(event.getId()).getNoticeChoice();
                if(noticeChoice==1) eventVO.setNoticeTime(new Date(date.getTime()-10*60*1000));
                else if(noticeChoice==2) eventVO.setNoticeTime(new Date(date.getTime()-30*60*1000));

                System.out.println(date);
                eventVO.setStartTime(date);
                eventVO.setEndTime(new Date(date.getTime()+duration));
                eventVOs.add(eventVO);
            }
        }
        return eventVOs;
    }
//1,1
    private List<Date> repeatStartTime(Date startTime, Date endTime, Event event){
        List<Date> dateList = new ArrayList<>();
        //不重复日程
        //日程结束时间小于区间开始时间或日程开始时间大于区间结束时间，该日程不会落在该时间区段上
        if(event.getEventFrequency()==0){
            if(event.getEndTime().getTime()<startTime.getTime()||event.getStartTime().getTime()>endTime.getTime()) return dateList;
            else{
                dateList.add(event.getStartTime());
                return dateList;
            }
        }
        //每日重复日程
        long timeInterval = 24 * 60 * 60 * 1000;
        //每周重复日程
        if(event.getEventFrequency()==2) timeInterval = 24 * 60 * 60 * 1000 * 7;
        //每年重复日程
        else if(event.getEventFrequency()==3){
            Calendar c = Calendar.getInstance();
            Date date = event.getStartTime();
            while (date.getTime()<endTime.getTime()){
                if(date.getTime()>startTime.getTime()) dateList.add(date);
                c.setTime(event.getStartTime());
                c.add(Calendar.YEAR, 1);
                date = c.getTime();
            }
        }

        //重复次数为终止条件
        if(event.getEventEndCondition()==0){
            long repeatedTimes;
            if(event.getStartTime().getTime()<startTime.getTime())
                repeatedTimes = (startTime.getTime()-event.getStartTime().getTime())/(timeInterval)+1;
            else repeatedTimes = 0;
            if(repeatedTimes<event.getRepeatTimes()){
                long unDoneTimes = event.getRepeatTimes()-repeatedTimes;
                Date date = new Date(event.getStartTime().getTime()+repeatedTimes* timeInterval);
                while (unDoneTimes>0&&date.getTime()<endTime.getTime()){
                    unDoneTimes--;
                    dateList.add(date);
                    date = new Date(date.getTime()+timeInterval);
                }
            }
        }
        //重复截止时间为终止条件
        else if(event.getEventEndCondition()==1){
            //当截止时间大于或等于区间开始时间，才会有重复日程
            if(event.getRepeatEndTime().getTime()>=startTime.getTime()){
                Date date;
                if(event.getStartTime().getTime()<startTime.getTime())
                    date = new Date(((startTime.getTime()-event.getStartTime().getTime())/( timeInterval)+1)* timeInterval+event.getStartTime().getTime());
                else date = event.getStartTime();
                while (date.getTime()<endTime.getTime()&&date.getTime()<event.getRepeatEndTime().getTime()){
                    dateList.add(date);
                    date = new Date(date.getTime()+timeInterval);
                }
            }
        }
        //无限重复
        else{
                Date date;
                if(event.getStartTime().getTime()<startTime.getTime())
                    date = new Date(((startTime.getTime()-event.getStartTime().getTime())/( timeInterval)+1)* timeInterval+event.getStartTime().getTime());
                else date = event.getStartTime();
                while (date.getTime()<endTime.getTime()){
                    dateList.add(date);
                    date = new Date(date.getTime()+timeInterval);
                }

        }
        return dateList;
    }
}

