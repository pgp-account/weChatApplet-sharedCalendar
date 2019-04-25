package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventTypeMapper {
    /**
     * 插入新的日程类型
     * @param eventType
     * @return
     */
    int insertEventType(EventType eventType);

    /**
     * 获取用户全部用户类型
     * @param userId
     * @return
     */
    List<EventType> selectEventTypeByUserId(int userId);

    /**
     * 根据typeId获取创建人昵称
     * @param typeId
     * @return
     */
    String selectCreatorNameByTypeId(int typeId);

    /**
     * 删除多个EventType
     * @param eventTypeIds
     */
    void deleteEventType(@Param("eventTypeIds") List<Integer> eventTypeIds);

    /**
     * 根据typeId获取eventType
     * @param typeId
     * @return
     */
    EventType selectEventTypeById(int typeId);
}
