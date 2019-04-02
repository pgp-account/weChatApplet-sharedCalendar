package com.bdilab.sharedcalendar.service.eventtype;

import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;

import java.util.List;

public interface EventTypeService {
    public boolean createEventType(EventType eventType);
    public List<EventType> getEventTypeList(int userId);
}
