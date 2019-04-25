package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.Event;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

public interface EventInfoMapper {
    /**
     * 根据日程id查看日程
     * @param event_id
     * @return
     */
    Event selectEventById(int event_id);

    /**
     * 根据日程开始时间搜索日程
     * @param start_time
     * @return
     */
    List<Event> searchEventsByDate(@Param("start_time") String start_time);
    /**
     * 插入新日程
     * @param event
     */
    int insertEvent( Event event);

    /**
     * 修改日程
     * @param event
     */
    int updateEvent(Event event);

    /**
     * 删除日程
     * @param eventIds
     */
    void deleteEvent(@Param("eventIds") List<Integer> eventIds);

    /**
     * 删除特定日程类型下的全部日程
     * @param eventType 日程类型id
     */
    void deleteEventByEventType(int eventType);

    /**
     * 获取同一日程类型下的全部日程
     * @param eventType
     * @return
     */
    List<Event> selectEventByEventType(int eventType);

    /**
     * 获取用户全部日程
     * @param userId
     * @return
     */
    List<Event> selectEventByUserId(int userId);
}
