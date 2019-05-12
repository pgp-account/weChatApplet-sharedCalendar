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
            case 7:return senderName+messageType.getTypeInfo()+"【"+eventTypeName+"】"+"，该类型现在的权限为<仅自己可见>。创建者不希望订阅者继续订阅，因而该日程类型已经从您的列表中移除。";
            case 8:return senderName+messageType.getTypeInfo()+"【"+eventTypeName+"】"+"，该类型现在的权限为<仅自己可见>。该类型的创建者允许订阅者继续订阅，这意味着您的订阅不会受到影响，但您无法分享再该类型。";
            default:return super.toString();
        }
    }
}

