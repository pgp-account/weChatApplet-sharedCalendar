package com.bdilab.sharedcalendar.service.event.impl;

import com.bdilab.sharedcalendar.mapper.EventInfoMapper;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventInfoMapper eventInfoMapper;
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
}
