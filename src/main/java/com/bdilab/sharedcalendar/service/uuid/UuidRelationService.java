package com.bdilab.sharedcalendar.service.uuid;

import com.bdilab.sharedcalendar.model.UuidRelation;

public interface UuidRelationService {
    /**
     * 获取shareCode
     * @param typeId
     * @return
     */
    UuidRelation generateShareCode(int typeId);

    /**
     * 检测shareCode是否可用
     * @param shareCode
     * @param userId
     * @return
     */
    Integer getShareCodeStatus(String shareCode,int userId);

    /**
     * 订阅
     * @param shareCode
     * @param userId
     * @param typeId
     * @return
     */
    boolean subscribeEventType(String shareCode, int userId, int typeId);

    /**
     * 取消订阅
     * @param userId
     * @param typeId
     * @return
     */
    boolean cancelSubscribe(int userId,int typeId);

    /**
     * 检测日程类型是否属于用户
     * @param userId
     * @param typeId
     * @return
     */
    boolean isMine(int userId,int typeId);
}
