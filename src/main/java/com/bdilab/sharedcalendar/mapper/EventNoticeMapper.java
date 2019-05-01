package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.EventNotice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EventNoticeMapper {
    /**
     * 插入日程提醒
     * @param eventNotice
     */
    int insertEventNotice(EventNotice eventNotice);

    /**
     * 根据提醒id获取日程提醒
     * @param noticeId
     */
    EventNotice getEventNotice(Integer noticeId);

    /**
     * 获取日程id当前未提醒的提醒
     * @param eventId
     * @return
     */
    EventNotice selectEventNoticeByEventId(@Param("eventId") Integer eventId);
    /**
     * 获取用户的提醒日程
     */
    List<EventNotice> selectEventNoticeByUserId(@Param("userId") Integer userId);
    /**
     * 设置日程的提醒状态为已提醒
     * @param noticeId
     */
    int setIsNotice(@Param("noticeId") Integer noticeId);

    /**
     * 修改日程提醒时间
     * @param noticeId
     * @param noticeTime
     */
    void setNoticeTime(@Param("noticeId") Integer noticeId,@Param("noticeTime") Date noticeTime);
    /**
     * 删除日程提醒
     * @return
     */
    void deleteEventNotice(@Param("noticeId") Integer noticeId);
}

