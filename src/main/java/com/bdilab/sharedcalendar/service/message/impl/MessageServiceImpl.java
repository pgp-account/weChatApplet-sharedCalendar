package com.bdilab.sharedcalendar.service.message.impl;

import com.bdilab.sharedcalendar.common.enums.MessageType;
import com.bdilab.sharedcalendar.common.message.MessageContent;
import com.bdilab.sharedcalendar.common.message.MessageTitle;
import com.bdilab.sharedcalendar.mapper.EventInfoMapper;
import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.mapper.MessageMapper;
import com.bdilab.sharedcalendar.mapper.PubMapper;
import com.bdilab.sharedcalendar.model.Message;
import com.bdilab.sharedcalendar.model.User;
import com.bdilab.sharedcalendar.model.UserModel;
import com.bdilab.sharedcalendar.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/28
 * Time:11:16
 */
@Service
public class MessageServiceImpl  implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private PubMapper pubMapper;

    @Autowired
    private EventInfoMapper eventInfoMapper;

    @Autowired
    private EventTypeMapper eventTypeMapper;


    @Override
    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = messageMapper.selectMessageByUserId(userId);
        return messages;
    }

    @Override
    public boolean deleteMessages(List<Integer> msgIds) {
        messageMapper.deleteMessageById(msgIds);
        return true;
    }

    @Override
    public boolean setMessagesRead(List<Integer> msgIds) {
        messageMapper.setMessageReadById(msgIds);
        return true;
    }

    @Override
    public void sendMessage(Message message, MessageType messageType) {

        String senderName=pubMapper.selectUserById(message.getFkMsgSender()).getNickName();
        String eventName=null;
        String typeName;
//        System.out.println("id = "+message.getFkMsgSender());
//        User sender = pubMapper.selectUserById(message.getFkMsgSender());
//        if(sender!=null) senderName = sender.getNickName();



        if(message.getFkEventType()==null){
            eventName = eventInfoMapper.selectEventById(message.getFkEventId()).getEventName();
            typeName = eventTypeMapper.selectEventTypeById(eventInfoMapper.selectEventById(message.getFkEventId()).getFkTypeId()).getTypeName();
        }
        else{
            typeName = eventTypeMapper.selectEventTypeById(message.getFkEventType()).getTypeName();
        }
        message.setMsgTitle(new MessageTitle(messageType,senderName).toString());
        message.setMsgContent(new MessageContent(messageType,senderName,typeName,eventName).toString());
        if(message.getFkEventType()!=null) messageMapper.insertMessageWithEventType(message);
        else messageMapper.insertMessageWithEvent(message);
    }
}
