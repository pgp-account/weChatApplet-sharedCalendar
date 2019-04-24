package com.bdilab.sharedcalendar.service.eventtype.impl;

import com.bdilab.sharedcalendar.mapper.EventInfoMapper;
import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import com.bdilab.sharedcalendar.service.eventtype.EventTypeService;
import com.bdilab.sharedcalendar.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventTypeServiceImpl implements EventTypeService {
    @Autowired
    private EventTypeMapper eventTypeMapper;

    @Autowired
    private EventInfoMapper eventInfoMapper;

    public boolean createEventType(EventType eventType){
        if(eventTypeMapper.insertEventType(eventType)==1) return true;
        return false;
    }

    public List<EventType> getEventTypeList(int userId){
        return eventTypeMapper.selectEventTypeById(userId);
    }

    //删除日程类型，同时删除其下的日程
    public void deleteEventTypes(List<Integer> eventTypeIds){
        //将日程类型下的日程全部删除
        for (int eventTypeId:eventTypeIds
             ) {
            eventInfoMapper.deleteEventByEventType(eventTypeId);
        }

    }

    //删除日程类型，同时将日程迁移至新的日程类型下
    public void deleteEventTypes(List<Integer> eventTypeIds,int newEventTypeId){
        //将日程迁移至新的日程类型下
        for (int eventTypeId:eventTypeIds
                ) {
            List<Event> events = eventInfoMapper.selectEventByEventType(eventTypeId);
            for (Event event:events
                 ) {
                event.setFkTypeId(newEventTypeId);
                eventInfoMapper.updateEvent(event);
            }
        }
        eventTypeMapper.deleteEventType(eventTypeIds);
    }
}
