package com.bdilab.sharedcalendar.common.message;

import com.bdilab.sharedcalendar.common.enums.MessageType;

/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/29
 * Time:14:58
 */
public class MessageContent {
    private MessageType messageType;
    private String senderName;
    private String eventTypeName;
    private String eventName;

    public MessageContent (MessageType messageType,String senderName,String eventTypeName,String eventName){
        this.messageType=messageType;
        this.eventName = eventName;
        this.senderName = senderName;
        this.eventTypeName = eventTypeName;
    }

    @Override
    public String toString() {
        switch (messageType.getValue()){
            case 0:return senderName+"在您订阅的日程类型【"+eventTypeName+"】中创建了新日程["+eventName+ "]。";
            case 1:return senderName+"在您订阅的日程类型【"+eventTypeName+"】中删除了日程["+eventName+ "]。";
            case 2:return senderName+"在您订阅的日程类型【"+eventTypeName+"】中更新了日程["+eventName+ "]。";
            case 3:return senderName+messageType.getTypeInfo()+"【"+eventTypeName+"】";
            case 4:return senderName+messageType.getTypeInfo()+"【"+eventTypeName+"】";
            case 5:return senderName+messageType.getTypeInfo()+"【"+eventTypeName+"】";
            case 6:return senderName+messageType.getTypeInfo()+"【"+eventTypeName+"】";
            default:return super.toString();
        }
    }
}

