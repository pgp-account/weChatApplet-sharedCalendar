package com.bdilab.sharedcalendar.service.eventtype;

import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import com.bdilab.sharedcalendar.vo.EventTypeInfoVO;
import com.bdilab.sharedcalendar.vo.SubEventTypeVO;
import io.swagger.models.auth.In;

import java.util.List;

public interface EventTypeService {
    /**
     * 创建日程类型
     * @param eventType
     * @return
     */
    boolean createEventType(EventType eventType);

    List<EventType> getEventTypeList(int userId);
    List<SubEventTypeVO> getEventSubTypeList(int userId);

    EventTypeInfoVO getEventTypeInfoById(int typeId);
    void deleteEventTypes(List<Integer> eventTypeIds);
    void deleteEventTypes(List<Integer> eventTypeIds, int newEventTypeId);
    boolean updateEventTypeInfo(EventType eventType,Integer operationType);
}
