package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventTypeMapper {
    int insertEventType(EventType eventType);
    List<EventType> selectEventTypeById(int userId);
    void deleteEventType(@Param("eventTypeIds") List<Integer> eventTypeIds);
}
