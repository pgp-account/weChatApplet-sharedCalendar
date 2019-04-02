package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.EventType;
import org.apache.ibatis.annotations.Param;

public interface EventTypeMapper {

    int insertEventType(@Param("eventType")EventType eventType);
}
