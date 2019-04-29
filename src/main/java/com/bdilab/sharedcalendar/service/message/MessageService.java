package com.bdilab.sharedcalendar.service.message;

import com.bdilab.sharedcalendar.common.enums.MessageType;
import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Message;
import com.bdilab.sharedcalendar.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/28
 * Time:11:15
 */
public interface MessageService{
    List<Message> getMessagesByUserId(int userId);
    boolean deleteMessages(List<Integer> msgIds);
    boolean setMessagesRead(List<Integer> msgIds);
    void sendMessage(Message message, MessageType messageType);
}
