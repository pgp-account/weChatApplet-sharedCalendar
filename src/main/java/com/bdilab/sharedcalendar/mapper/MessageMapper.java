package com.bdilab.sharedcalendar.mapper;

import com.bdilab.sharedcalendar.model.Message;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageMapper {
    List<Message> selectMessageByUserId(int userId);
    int deleteMessageById(@Param("msgIds")List<Integer> msgIds);
    int setMessageReadById(@Param("msgIds")List<Integer> msgIds);
    int insertMessageWithEvent(Message message);
    int insertMessageWithEventType(Message message);
}