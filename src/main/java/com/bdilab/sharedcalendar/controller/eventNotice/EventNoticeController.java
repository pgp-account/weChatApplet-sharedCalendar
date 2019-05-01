package com.bdilab.sharedcalendar.controller.eventNotice;
import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventNotice;
import com.bdilab.sharedcalendar.service.event.EventService;
import com.bdilab.sharedcalendar.service.eventnotice.EventNoticeService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EventNoticeController {
    @Autowired
    EventNoticeService eventNoticeService;
    @Autowired
    EventService eventService;

    /**
     * 根据userId获取提醒日程
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "eventNotice/getNoticeList",method = RequestMethod.GET)
    public ResponseResult getNoticeList(HttpSession httpSession)throws Exception{
        Integer userId = Integer.valueOf(httpSession.getAttribute("user_id").toString());
        //Integer userId = 1;
        List<EventNotice> eventNotices = eventNoticeService.getEventNoticeByUserId(userId);
        Map<String,Object> data = new HashMap<>();
        data.put("eventNotices",eventNotices);
        data.put("total",eventNotices.size());
        if (eventNotices.size()>0){
            return new ResponseResult(true,"101","获取提醒日程成功",data);
        }else {
            return new ResponseResult(false,"102","获取提醒日程失败",data);
        }
    }

    /**
     * 根据日程的重复信息，当前提醒触发后，生成下一个提醒
     * @param noticeId
     * @param httpSession
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "eventNotice/setNextNotice",method = RequestMethod.POST)
    public ResponseResult setNextNotice(@RequestParam(value = "noticeId") Integer noticeId,
                                        @RequestParam(value = "eventFrequency") Integer eventFrequency,
                                        HttpSession httpSession)throws Exception{
        EventNotice eventNotice = eventNoticeService.selectEventNoticeById(noticeId);
        eventNoticeService.setNoticeRead(noticeId);
        Integer typeId = eventNotice.getFkTypeId();
        Integer eventId = eventNotice.getFkEventId();
        Integer userId = eventNotice.getFkUserId();
        Date currntNoticeTime = eventNotice.getNoticeTime();
        Date nextNoticeTime = new Date();
        //根据日程频率设置下一个提醒时间
        switch (eventFrequency){
            case 1:
                nextNoticeTime = new Date(currntNoticeTime.getTime()+24*60*60*1000);
                break;
            case 2:
                nextNoticeTime = new Date(currntNoticeTime.getTime()+7*24*60*60*1000);
                break;
            case 3:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currntNoticeTime);
                calendar.add(Calendar.YEAR,1);
                nextNoticeTime = calendar.getTime();
                break;
        }
        EventNotice eventNotice1 = eventNoticeService.createEventNotice(typeId,eventId,userId,nextNoticeTime);
        Map<String,Object> data = new HashMap<>();
        data.put("eventNotice",eventNotice1);
        return new ResponseResult(true,"xxx","创建下一次提醒成功",data);
    }

}
