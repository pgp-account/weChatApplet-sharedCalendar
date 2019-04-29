package com.bdilab.sharedcalendar.controller.message;

import com.bdilab.sharedcalendar.common.response.MetaData;
import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Message;
import com.bdilab.sharedcalendar.service.message.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:hh
 * Date:2019/4/28
 * Time:11:14
 */
@Controller
/**
 * CrossOrigin 注解，允许跨域
 */
@CrossOrigin
@Api(value = "message controller")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 用户获取所有消息
     * @param httpSession
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/getMessages", method = RequestMethod.GET)
    public ResponseResult getMessages(HttpSession httpSession) {
        int userId = 3;
        //int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        List<Message> messages = messageService.getMessagesByUserId(userId);
        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("Messages",messages);
        data.put("Total",messages.size());
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","获取用户所有消息列表成功"));
        return responseResult;
    }

    /**
     * 批量删除消息
     * @param msgIds
     * @param httpSession
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/deleteMessages", method = RequestMethod.POST)
    public ResponseResult deleteMessages(@RequestParam String msgIds,HttpSession httpSession) {
        System.out.println("msgIds="+msgIds);
        String[] messageIds=msgIds.split(",");
        if(messageIds.length==0){
            return new ResponseResult(false,"002","参数错误，日程类型id为空");
        }

        List<Integer> list=new ArrayList<>();
        for(int i=0;i<messageIds.length;i++){
            list.add(Integer.valueOf(messageIds[i]));
        }
        if(messageService.deleteMessages(list))
            return new ResponseResult(true,"001","删除消息成功",null);
        return new ResponseResult(true,"002","删除消息失败",null);
    }

    /**
     * 批量设置消息已读
     * @param msgIds
     * @param httpSession
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/setMessagesRead", method = RequestMethod.POST)
    public ResponseResult setMessagesRead(@RequestParam String msgIds,HttpSession httpSession) {
        System.out.println("msgIds="+msgIds);
        String[] messageIds=msgIds.split(",");
        if(messageIds.length==0){
            return new ResponseResult(false,"002","参数错误，日程类型id为空");
        }

        List<Integer> list=new ArrayList<>();
        for(int i=0;i<messageIds.length;i++){
            list.add(Integer.valueOf(messageIds[i]));
        }
        if(messageService.setMessagesRead(list))
            return new ResponseResult(true,"001","设置消息已读成功",null);
        return new ResponseResult(true,"002","设置消息已读失败",null);
    }

}
