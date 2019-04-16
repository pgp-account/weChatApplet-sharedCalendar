package com.bdilab.sharedcalendar.service.uuid.impl;

import com.bdilab.sharedcalendar.mapper.UuidRelationMapper;
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

    public UuidRelation generateShareCode(int typeId){
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
}
