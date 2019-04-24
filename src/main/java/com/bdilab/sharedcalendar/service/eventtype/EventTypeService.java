package com.bdilab.sharedcalendar.service.eventtype;

import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import io.swagger.models.auth.In;

import java.util.List;

public interface EventTypeService {
    public boolean createEventType(EventType eventType);
    public List<EventType> getEventTypeList(int userId);
    public void deleteEventTypes(List<Integer> eventTypeIds);
    public void deleteEventTypes(List<Integer> eventTypeIds, int newEventTypeId);
}
