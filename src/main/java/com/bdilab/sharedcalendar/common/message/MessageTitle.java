package com.bdilab.sharedcalendar.common.message;
import com.bdilab.sharedcalendar.common.enums.MessageType;


/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/28
 * Time:11:28
 */
public class MessageTitle {
    private MessageType messageType;
    private String senderName;

    public MessageTitle(MessageType messageType,String recieverName){
        this.messageType = messageType;
        this.senderName = recieverName;
    }

    @Override
    public String toString() {
        return senderName+messageType.getTypeInfo();
    }
}
