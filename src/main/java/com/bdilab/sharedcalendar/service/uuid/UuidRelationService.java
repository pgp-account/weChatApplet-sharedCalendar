package com.bdilab.sharedcalendar.service.uuid;

import com.bdilab.sharedcalendar.model.UuidRelation;

public interface UuidRelationService {
    UuidRelation generateShareCode(int typeId);
    Integer getShareCodeStatus(String shareCode,int userId);
    boolean subscribeEventType(String shareCode, int userId, int typeId);
    boolean cancelSubscribe(int userId,int typeId);
    boolean isMine(int userId,int typeId);
}
