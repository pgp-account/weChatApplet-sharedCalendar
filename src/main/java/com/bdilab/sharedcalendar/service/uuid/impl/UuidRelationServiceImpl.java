package com.bdilab.sharedcalendar.service.uuid.impl;

import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.mapper.UuidRelationMapper;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.SubscribedRelation;
import com.bdilab.sharedcalendar.model.UuidRelation;
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

    @Override
    public boolean isMine(int userId, int typeId) {
        EventType eventType = eventTypeMapper.selectEventTypeById(typeId);
        return eventType.getFkCreatorId()==userId;
    }

    @Override
    public Integer getShareCodeStatus(String shareCode,int userId) {
        UuidRelation uuidRelation = uuidRelationMapper.selectUuidRelationByUuid(shareCode);
        //未被删除且未使用且未过期且之前未订阅过该类型
        if(eventTypeMapper.selectEventTypeById(uuidRelation.getFkTypeId())!=null&&uuidRelation.getIsUsed()==0&&uuidRelation.getExpireTime().getTime()>new Date().getTime()&&uuidRelationMapper.selectSubscribedRelationByUserIdAndTypeId(userId,uuidRelation.getFkTypeId())==null)
            return uuidRelation.getFkTypeId();
        return 0;
    }

    @Override
    public boolean subscribeEventType(String shareCode,int userId,int typeId) {
        SubscribedRelation subscribedRelation = new SubscribedRelation();
        UuidRelation uuidRelation = uuidRelationMapper.selectUuidRelationByUuid(shareCode);
        subscribedRelation.setFkTypeId(uuidRelation.getFkTypeId());
        subscribedRelation.setFkUserId(userId);
        subscribedRelation.setFkCreatorId(eventTypeMapper.selectEventTypeById(typeId).getFkCreatorId());
        subscribedRelation.setSubscribeTime(new Date());
        return uuidRelationMapper.insertSubscribeRelation(subscribedRelation)==1;
    }

    @Override
    public boolean cancelSubscribe(int userId, int typeId) {
        return uuidRelationMapper.deleteByUserIdAndTypeId(userId,typeId)==1;
    }
}
