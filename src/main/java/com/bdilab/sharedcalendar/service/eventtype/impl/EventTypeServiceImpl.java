package com.bdilab.sharedcalendar.service.eventtype.impl;

import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
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

    public boolean createEventType(EventType eventType){
        if(eventTypeMapper.insertEventType(eventType)==1) return true;
        return false;
    }

    public List<EventType> getEventTypeList(int userId){
        return eventTypeMapper.selectEventTypeById(userId);
    }


}
