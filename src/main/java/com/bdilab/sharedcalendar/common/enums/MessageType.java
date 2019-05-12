package com.bdilab.sharedcalendar.common.enums;

/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/28
 * Time:11:29
 */
public enum MessageType {

    CREATE_EVENT(0, "创建了日程"),DELETE_EVENT(1, "删除了日程"),UPDATE_EVENT(2,"更新了日程"),
    SUBSCRIBE(3,"订阅了您的日程类型"),CANCEL_SUB(4,"不再订阅您的日程类型"),
    UPDATE_EVENT_TYPE(5,"更新了日程类型"),DELETE_EVENT_TYPE(6,"删除了日程类型"),
    UPDATE_TYPE_TRAN_1(7,"更新了日程类型"),
    UPDATE_TYPE_TRAN_2(8,"更新了日程类型");
    private int value;

    private String typeInfo;

    MessageType(int value, String operationName) {
        this.value = value;
        this.typeInfo = operationName;
    }

    public int getValue() {
        return value;
    }

    public String getTypeInfo() {
        return typeInfo;
    }
}
