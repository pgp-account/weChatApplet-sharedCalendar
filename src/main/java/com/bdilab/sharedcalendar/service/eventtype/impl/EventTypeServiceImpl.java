package com.bdilab.sharedcalendar.service.eventtype.impl;

import com.bdilab.sharedcalendar.common.enums.MessageType;
import com.bdilab.sharedcalendar.mapper.*;
import com.bdilab.sharedcalendar.model.*;
import com.bdilab.sharedcalendar.service.eventtype.EventTypeService;
import com.bdilab.sharedcalendar.service.message.MessageService;
import com.bdilab.sharedcalendar.utils.UuidGenerator;
import com.bdilab.sharedcalendar.vo.EventTypeInfoVO;
import com.bdilab.sharedcalendar.vo.SubEventTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventTypeServiceImpl implements EventTypeService {
    @Autowired
    private EventTypeMapper eventTypeMapper;

    @Autowired
    private EventInfoMapper eventInfoMapper;

    @Autowired
    private UuidRelationMapper uuidRelationMapper;

    @Autowired
    private PubMapper pubMapper;

    @Autowired
    private MessageService messageService;

    public boolean createEventType(EventType eventType){
        if(eventTypeMapper.insertEventType(eventType)==1) return true;
        return false;
    }

    public List<EventType> getEventTypeList(int userId){
        //用户自己创建的日程类型
        return eventTypeMapper.selectEventTypeByUserId(userId);
    }

    @Override
    public List<SubEventTypeVO> getEventSubTypeList(int userId) {
        //用户订阅的日程类型
        List<SubscribedRelation> SRlist = uuidRelationMapper.selectSubscribedRelationByUserId(userId);
        List<SubEventTypeVO> subEventTypeVOS = new ArrayList<>();
        for (SubscribedRelation sr:SRlist
             ) {
            SubEventTypeVO subEventTypeVO = new SubEventTypeVO(eventTypeMapper.selectEventTypeById(sr.getFkTypeId()));
            subEventTypeVO.setSubscribedDate(sr.getSubscribeTime());
            subEventTypeVO.setCreatorName(pubMapper.selectUserById(sr.getFkCreatorId()).getNickName());
            subEventTypeVOS.add(subEventTypeVO);
        }
        return subEventTypeVOS;
    }

    //删除日程类型，同时删除其下的日程
    public void deleteEventTypes(List<Integer> eventTypeIds){
        //将日程类型下的日程全部删除
        for (int eventTypeId:eventTypeIds
             ) {
            //发送消息给所有订阅该类型的用户
            List<SubscribedRelation> subscribedRelations = uuidRelationMapper.selectSubscribedRelationByTypeId(eventTypeId);
            send(subscribedRelations);
            //删除日程类型
            eventInfoMapper.deleteEventByEventType(eventTypeId);
            //删除该日程的所有订阅联系
            uuidRelationMapper.deleteByTypeId(eventTypeId);

        }
        eventTypeMapper.deleteEventType(eventTypeIds);

    }

    //删除日程类型，同时将日程迁移至新的日程类型下
    public void deleteEventTypes(List<Integer> eventTypeIds,int newEventTypeId){
        //将日程迁移至新的日程类型下
        for (int eventTypeId:eventTypeIds
                ) {
            List<Event> events = eventInfoMapper.selectEventByEventType(eventTypeId);
            //发送消息
            List<SubscribedRelation> subscribedRelations = uuidRelationMapper.selectSubscribedRelationByTypeId(eventTypeId);
            send(subscribedRelations);
            //删除该日程的所有订阅联系
            uuidRelationMapper.deleteByTypeId(eventTypeId);
            for (Event event:events
                 ) {
                //迁移日程
                event.setFkTypeId(newEventTypeId);
                eventInfoMapper.updateEvent(event);
            }
        }
        //删除日程类型
        eventTypeMapper.deleteEventType(eventTypeIds);

    }

    @Override
    public boolean updateEventTypeInfo(EventType eventType,Integer operationType) {

        if(eventTypeMapper.updateEventTypeInfo(eventType)==1){
            //发送消息给所有订阅该类型的用户
            List<SubscribedRelation> subscribedRelations = uuidRelationMapper.selectSubscribedRelationByTypeId(eventType.getId());
            if(operationType!=null){
                //不允许继续订阅
                if(operationType == 1){
                    //发送消息
                    for (SubscribedRelation subRel:subscribedRelations
                            ) {
                        Message message = new Message();
                        message.setIsRead(0);
                        message.setFkMsgSender(subRel.getFkCreatorId());
                        message.setFkMsgReciever(subRel.getFkUserId());
                        message.setCreateTime(new Date());
                        message.setFkEventType(subRel.getFkTypeId());
                        messageService.sendMessage(message, MessageType.UPDATE_TYPE_TRAN_1);
                    }
                    //删除订阅关系
                    uuidRelationMapper.deleteByTypeId(eventType.getId());
                }
                //允许继续订阅
                if(operationType == 2){
                    //发送消息
                    for (SubscribedRelation subRel:subscribedRelations
                            ) {
                        Message message = new Message();
                        message.setIsRead(0);
                        message.setFkMsgSender(subRel.getFkCreatorId());
                        message.setFkMsgReciever(subRel.getFkUserId());
                        message.setCreateTime(new Date());
                        message.setFkEventType(subRel.getFkTypeId());
                        messageService.sendMessage(message, MessageType.UPDATE_TYPE_TRAN_2);
                    }
                }
            }
            else{
                //未更新类型透明度
                for (SubscribedRelation subRel:subscribedRelations
                        ) {
                    Message message = new Message();
                    message.setIsRead(0);
                    message.setFkMsgSender(subRel.getFkCreatorId());
                    message.setFkMsgReciever(subRel.getFkUserId());
                    message.setCreateTime(new Date());
                    message.setFkEventType(subRel.getFkTypeId());
                    messageService.sendMessage(message, MessageType.UPDATE_EVENT_TYPE);
                }
            }
            return true;
        }
        return false;
    }




    @Override
    public EventTypeInfoVO getEventTypeInfoById(int typeId) {
        EventType eventType =  eventTypeMapper.selectEventTypeById(typeId);
        EventTypeInfoVO eventTypeInfoVO = new EventTypeInfoVO(eventType);
        eventTypeInfoVO.setCreatorName(pubMapper.selectUserById(eventType.getFkCreatorId()).getNickName());
        return eventTypeInfoVO;
    }

    void send(List<SubscribedRelation> subscribedRelations){
        for (SubscribedRelation subRel:subscribedRelations
                ) {
            Message message = new Message();
            message.setIsRead(0);
            message.setFkMsgSender(subRel.getFkCreatorId());
            message.setFkMsgReciever(subRel.getFkUserId());
            message.setCreateTime(new Date());
            message.setFkEventType(subRel.getFkTypeId());
            messageService.sendMessage(message, MessageType.DELETE_EVENT_TYPE);
        }
    }
}
