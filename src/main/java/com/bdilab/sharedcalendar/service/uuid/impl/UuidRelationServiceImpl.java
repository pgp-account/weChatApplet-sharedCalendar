package com.bdilab.sharedcalendar.service.uuid.impl;

import com.bdilab.sharedcalendar.common.enums.MessageType;
import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.mapper.MessageMapper;
import com.bdilab.sharedcalendar.mapper.UuidRelationMapper;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.Message;
import com.bdilab.sharedcalendar.model.SubscribedRelation;
import com.bdilab.sharedcalendar.model.UuidRelation;
import com.bdilab.sharedcalendar.service.message.MessageService;
import com.bdilab.sharedcalendar.service.uuid.UuidRelationService;
import com.bdilab.sharedcalendar.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UuidRelationServiceImpl implements UuidRelationService {
    @Autowired
    UuidRelationMapper uuidRelationMapper;
    @Autowired
    EventTypeMapper eventTypeMapper;
    @Autowired
    MessageService messageService;
    /**
     * 获取shareCode
     * @param typeId
     * @return
     */
    public UuidRelation generateShareCode(int typeId){
        if(eventTypeMapper.selectEventTypeById(typeId)!=null){
            UuidRelation uuidRelation = new UuidRelation();
            uuidRelation.setFkTypeId(typeId);
            uuidRelation.setUuid(UuidGenerator.generate());
            uuidRelation.setIsUsed(0);
            Date now = new Date();
            //有效时间为三天
            uuidRelation.setExpireTime(new Date(now.getTime() + 3 * 24 * 60 * 60 * 1000));
            if(uuidRelationMapper.insertUuidRelation(uuidRelation)!=1) return null;
            return uuidRelation;
        }
        return null;
    }



    /**
     * 检测shareCode是否可用
     * @param shareCode
     * @param userId
     * @return
     */
    @Override
    public Integer getShareCodeStatus(String shareCode,int userId) {
        UuidRelation uuidRelation = uuidRelationMapper.selectUuidRelationByUuid(shareCode);
        //未被删除且未使用且未过期且之前未订阅过该类型
        if(eventTypeMapper.selectEventTypeById(uuidRelation.getFkTypeId())!=null&&uuidRelation.getIsUsed()==0&&uuidRelation.getExpireTime().getTime()>new Date().getTime()&&uuidRelationMapper.selectSubscribedRelationByUserIdAndTypeId(userId,uuidRelation.getFkTypeId())==null)
            return uuidRelation.getFkTypeId();
        return 0;
    }

    /**
     * 订阅
     * @param shareCode
     * @param userId
     * @param typeId
     * @return
     */
    @Override
    public boolean subscribeEventType(String shareCode,int userId,int typeId) {
        SubscribedRelation subscribedRelation = new SubscribedRelation();
        UuidRelation uuidRelation = uuidRelationMapper.selectUuidRelationByUuid(shareCode);
        subscribedRelation.setFkTypeId(uuidRelation.getFkTypeId());
        subscribedRelation.setFkUserId(userId);
        subscribedRelation.setFkCreatorId(eventTypeMapper.selectEventTypeById(typeId).getFkCreatorId());
        subscribedRelation.setSubscribeTime(new Date());
        //插入SubscribeRelation表
        if(uuidRelationMapper.insertSubscribeRelation(subscribedRelation)==1){
            //将shareCode设为已使用
            uuidRelationMapper.setIsUsed(uuidRelation.getId(),1);
            //增加订阅人
            eventTypeMapper.increaseSubNum(typeId);
            //发送消息
            Message message = new Message();
            message.setIsRead(0);
            message.setFkMsgSender(subscribedRelation.getFkUserId());
            message.setFkMsgReciever(subscribedRelation.getFkCreatorId());
            message.setCreateTime(new Date());
            message.setFkEventType(subscribedRelation.getFkTypeId());
            messageService.sendMessage(message, MessageType.SUBSCRIBE);
            return true;
        }
        return false;
    }

    /**
     * 取消订阅
     * @param userId
     * @param typeId
     * @return
     */
    @Override
    public boolean cancelSubscribe(int userId, int typeId) {
        //System.out.println("userID="+userId+"\ntypeID="+typeId);
        SubscribedRelation subscribedRelation = uuidRelationMapper.selectSubscribedRelationByUserIdAndTypeId(userId,typeId);
        //System.out.println("userID:"+subscribedRelation.getFkUserId());
        if(uuidRelationMapper.deleteByUserIdAndTypeId(userId,typeId)==1){
            //减少订阅人
            eventTypeMapper.decreaseSubNum(typeId);
            //发送消息
            Message message = new Message();
            message.setIsRead(0);
            message.setFkMsgSender(subscribedRelation.getFkUserId());
            message.setFkMsgReciever(subscribedRelation.getFkCreatorId());
            message.setCreateTime(new Date());
            message.setFkEventType(subscribedRelation.getFkTypeId());
            messageService.sendMessage(message, MessageType.CANCEL_SUB);
            return true;
        }
        return false;
    }

    /**
     * 检测日程类型是否属于用户
     * @param userId
     * @param typeId
     * @return
     */
    @Override
    public boolean isMine(int userId, int typeId) {
        EventType eventType = eventTypeMapper.selectEventTypeById(typeId);
        return eventType.getFkCreatorId()==userId;
    }
}
