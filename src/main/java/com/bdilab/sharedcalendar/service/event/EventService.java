package com.bdilab.sharedcalendar.service.event;

import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.vo.EventVO;

import java.util.Date;
import java.util.List;

public interface EventService {
    /**
     * 创建日程
     * @param event
     * @return
     */
    boolean createEvent(Event event);

    /**
     * 根据日程id查找日程
     * @return
     */
    Event selectEventById(Integer event_id);
    /**
     * 根据日期开始时间搜索日程
     * @return
     */
    List<Event> searchEventsByDate(String start_time);

    /**
     * 修改日程
     * @param event
     * @return
     */
    boolean updateEvent(Event event);

    /**
     * 删除日程
     * @param eventIds
     * @return
     */
    boolean deleteEvent(List<Integer> eventIds);

    List<EventVO> getEventVOsByDate(Date startTime, Date endTime, int userId);

    List<EventVO> getEventVOsByTypeAndTime(Date startTime,Date endTime, int typeId);
}
