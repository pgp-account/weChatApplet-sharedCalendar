package com.bdilab.sharedcalendar.service.eventtype.impl;

import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.service.eventtype.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTypeServiceImpl implements EventTypeService {
    @Autowired
    private EventTypeMapper eventTypeMapper;

    public boolean createEventType(EventType eventType){
        if(eventTypeMapper.insertEventType(eventType)==1) return true;
        return false;
    }
}
