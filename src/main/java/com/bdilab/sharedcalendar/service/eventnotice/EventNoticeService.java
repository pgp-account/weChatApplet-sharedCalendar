package com.bdilab.sharedcalendar.service.eventnotice;


import com.bdilab.sharedcalendar.model.EventNotice;

import java.util.Date;
import java.util.List;

public interface EventNoticeService {
    /**
     * 创建日程提醒
     * @param
     * @return
     */
    EventNotice createEventNotice(Integer typeId,Integer eventId,Integer userId, Date noticeTime);

    /**
     * 根据提醒id获取日程提醒
     * @param noticeId
     * @return
     */
    EventNotice selectEventNoticeById(Integer noticeId);

    /**
     * 获取用户的所有未提醒的提醒
     * @param userId
     * @return
     */
    List<EventNotice> getEventNoticeByUserId(Integer userId);
    /**
     * 设置提醒状态为已提醒
     * @param noticeId
     * @return
     */
    boolean setNoticeRead(Integer noticeId);

    /**
     * 重置日程提醒时间
     * @param noticeId
     * @param noticeTime
     * @return
     */
    boolean resetNoticeTime(Integer noticeId, Date noticeTime);

    /**
     * 删除日程提醒
     * @return
     */
    boolean deleteEventNoticeById(Integer noticeId);

}
