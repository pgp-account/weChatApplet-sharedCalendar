package com.bdilab.sharedcalendar.controller.event;

import com.bdilab.sharedcalendar.common.response.MetaData;
import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventNotice;
import com.bdilab.sharedcalendar.service.event.EventService;

import com.bdilab.sharedcalendar.service.eventnotice.EventNoticeService;
import com.bdilab.sharedcalendar.service.eventtype.EventTypeService;
import com.bdilab.sharedcalendar.service.pub.PubService;
import com.bdilab.sharedcalendar.vo.EventTypeInfoVO;
import com.bdilab.sharedcalendar.vo.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventNoticeService eventNoticeService;
    @Autowired
    private EventTypeService eventTypeService;
    @Autowired
    private PubService pubService;
    /**
     * 创建日程
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/event/createEvent", method = RequestMethod.POST)
    public ResponseResult createEvent(@RequestParam(value = "eventName") String eventName,
                                      @RequestParam(value = "startTime")Date startTime,
                                      @RequestParam(value = "endTime") Date endTime,
                                      @RequestParam(value = "fkTypeId") Integer fkTypeId,
                                      @RequestParam(value = "eventFrequency") Integer eventFrequency,
                                      @RequestParam(value = "eventEndCondition") Integer eventEndCondition,
                                      @RequestParam(value = "repeatEndTime",required = false) Date repeatEndTime,
                                      @RequestParam(value = "repeatTimes",required = false) Integer repeatTimes,
                                      @RequestParam(value = "noticeChoice") Integer noticeChoice,
                                      @RequestParam(value = "eventContent") String eventContent,
                                      HttpSession httpSession) throws Exception{
        ResponseResult responseResult = new ResponseResult();
        Event event = new Event();
        EventNotice eventNotice = new EventNotice();
        Integer creatorId = (Integer) httpSession.getAttribute("user_id");
//        String date = "2019-05-11 12:00:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        startTime = sdf.parse(date);
//        endTime = new Date(startTime.getTime() + 60 * 60 * 1000);
//        int creatorId =5;


        event.setFkCreatorId(creatorId);
        event.setEventName(eventName);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setFkTypeId(fkTypeId);
        event.setEventFrequency(eventFrequency);
        event.setNoticeChoice(noticeChoice);
        event.setEventContent(eventContent);
        event.setEventEndCondition(eventEndCondition);
        //重复次数初始化为0
        event.setRepeatTimes(0);
        //重复截止时间初始化为null
        event.setRepeatEndTime(null);
        //当前重复次数初始化为0
        event.setCurrentRepeatTimes(0);
        //重复终止条件为0设置重复次数，重复终止条件为1设置重复截止时间，否则不设置
        if(eventEndCondition == 0){
            event.setRepeatTimes(repeatTimes);
        }else if (eventEndCondition == 1){
            event.setRepeatEndTime(repeatEndTime);
        }else {

        }
        if(eventService.createEvent(event)){
            switch (noticeChoice){
                case 0:
                    eventNotice = null;
                    break;
                case 1:
                    eventNotice = eventNoticeService.createEventNotice(event.getFkTypeId(),event.getId(),event.getFkCreatorId(),new Date(event.getStartTime().getTime()-10*60*1000));
                    break;
                case 2:
                    eventNotice = eventNoticeService.createEventNotice(event.getFkTypeId(),event.getId(),event.getFkCreatorId(),new Date(event.getStartTime().getTime()-30*60*1000));
                    break;
            }
        }
        Map<String,Object> data = new HashMap<>();
        data.put("event",event);
        data.put("eventNotice",eventNotice);
        responseResult.setSuccess(true);
        responseResult.setCode("301");
        responseResult.setMessage("添加日程成功");
        responseResult.setData(data);
        return responseResult;
    }

    /**
     * 更新日程
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "event/updateEvent", method = RequestMethod.POST)
    public ResponseResult updateEvent(@RequestParam(value = "eventId") Integer eventId,
                                      @RequestParam(value = "noticeId",required = false) Integer noticeId,
                                      @RequestParam(value = "eventName",required = false) String eventName,
                                      @RequestParam(value = "startTime",required = false)Date startTime,
                                      @RequestParam(value = "endTime",required = false) Date endTime,
                                      @RequestParam(value = "fkTypeId",required = false) Integer fkTypeId,
                                      @RequestParam(value = "eventFrequency",required = false) Integer eventFrequency,
                                      @RequestParam(value = "eventEndCondition",required = false) Integer eventEndCondition,
                                      @RequestParam(value = "repeatEndTime",required = false) Date repeatEndTime,
                                      @RequestParam(value = "repeatTimes",required = false) Integer repeatTimes,
                                      @RequestParam(value = "noticeChoice",required = false) Integer noticeChoice,
                                      @RequestParam(value = "eventContent",required = false) String eventContent,
                                      HttpSession httpSession) throws Exception{
        ResponseResult responseResult = new ResponseResult();
        Event event = eventService.selectEventById(eventId);
        EventNotice eventNotice = new EventNotice();
        if (eventName!=null){event.setEventName(eventName);}
        if (startTime!=null){event.setStartTime(startTime);}
        if (endTime!=null){event.setEndTime(endTime);}
        if (fkTypeId!=null){event.setFkTypeId(fkTypeId);}
        if (eventFrequency!=null){event.setEventFrequency(eventFrequency);}
        if (eventContent!=null)event.setEventContent(eventContent);
        if (eventEndCondition!=null){event.setEventEndCondition(eventEndCondition);}
        //重复终止条件为0设置重复次数，重复终止条件为1设置重复截止时间，否则不设置
        if (eventEndCondition!=null){
            event.setEventEndCondition(eventEndCondition);
            //重复终止条件为0设置重复次数，重复终止条件为1设置重复截止时间，否则不设置
            if(eventEndCondition == 0){
                event.setRepeatTimes(repeatTimes);
            }else if (eventEndCondition == 1){
                event.setRepeatEndTime(repeatEndTime);
            }
        }
        if (noticeId!=null){
            switch (noticeChoice){
                case 0:
                    eventNoticeService.deleteEventNoticeById(noticeId);
                    eventNotice = null;
                    break;
                case 1:
                    eventNoticeService.resetNoticeTime(noticeId,new Date(event.getStartTime().getTime()-10*60*1000));
                    eventNotice = eventNoticeService.selectEventNoticeById(noticeId);
                    break;
                case 2:
                    eventNoticeService.resetNoticeTime(noticeId,new Date(event.getStartTime().getTime()-30*60*1000));
                    eventNotice = eventNoticeService.selectEventNoticeById(noticeId);
                    break;
            }
            event.setNoticeChoice(noticeChoice);
        }else {
            switch (noticeChoice){
                case 0:
                    eventNotice = null;
                    break;
                case 1:
                    eventNotice = eventNoticeService.createEventNotice(event.getFkTypeId(),event.getId(),event.getFkCreatorId(),new Date(event.getStartTime().getTime()-10*60*1000));
                    break;
                case 2:
                    eventNotice = eventNoticeService.createEventNotice(event.getFkTypeId(),event.getId(),event.getFkCreatorId(),new Date(event.getStartTime().getTime()-30*60*1000));
                    break;
            }
            event.setNoticeChoice(noticeChoice);
        }
        eventService.updateEvent(event);
        Map<String,Object> data = new HashMap<>();
        data.put("event",event);
        data.put("eventNotice",eventNotice);
        responseResult.setSuccess(true);
        responseResult.setCode("401");
        responseResult.setMessage("更新日程成功");
        responseResult.setData(data);
        return responseResult;
    }

    /**
     * 查看日程
     * @param eventId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "event/selectEvent",method = RequestMethod.POST)
    public ResponseResult selectEvent(@RequestParam("eventId") Integer eventId) throws Exception{
        Event event = eventService.selectEventById(eventId);
        EventVO eventVO = new EventVO(event);
        EventNotice eventNotice = new EventNotice();
        if ((eventNotice=eventNoticeService.selectEventNoticeByEventId(eventId))!=null){
            eventVO.setNoticeTime(eventNotice.getNoticeTime());
        }
        String nickName = pubService.getUserInfo(event.getFkCreatorId()).getNickName();
        if (nickName!=null) eventVO.setCreatorName(nickName);
        //System.out.println("event type id "+event.getFkTypeId());
        EventTypeInfoVO eventTypeInfoVO = eventTypeService.getEventTypeInfoById(event.getFkTypeId());
        eventVO.setEventTypeName(eventTypeInfoVO.getTypeName());

        if (eventVO!=null){
            return new ResponseResult(true,"001","查看日程成功",eventVO);
        }else {
            return new ResponseResult(false,"002","查看日程失败",eventVO);
        }
    }

    /**
     * 删除日程
     */
    @ResponseBody
    @RequestMapping(value = "event/deleteEvent", method = RequestMethod.POST)
    public ResponseResult deleteEvent(@RequestParam("eventIds") String eventIds) throws Exception{
        String[] stringEventIds = eventIds.split(",");
        if (stringEventIds.length==0){
            return new ResponseResult(false,"003","日程id为空");
        }
        List<Integer> listEventIds = new ArrayList<>();
        for (String s:stringEventIds){
            listEventIds.add(Integer.valueOf(s));
        }
        if (eventService.deleteEvent(listEventIds)){
            return new ResponseResult(true,"001","删除多条日程成功",listEventIds);
        }else {
            return new ResponseResult(false,"002","删除多条日程失败");
        }
    }

    /**
     * 获取用户某个时间段的日程列表信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "event/getEventListByTime", method = RequestMethod.POST)
    public ResponseResult getEvents(@RequestParam Date startTime,
                                    @RequestParam Date endTime,
                                    HttpSession httpSession) throws Exception{
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //int userId = 2;
//        String date = "2019-05-11 00:00:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        startTime = sdf.parse(date);
//        endTime = new Date(startTime.getTime() + 24 * 60 * 60 * 1000 * 7);

        List<EventVO> eventVOs = eventService.getEventVOsByDate(startTime,endTime,userId);
        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("EventVOs",eventVOs);
        data.put("Total",eventVOs.size());
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","获取用户所有日程成功"));
        return responseResult;
    }

    @ResponseBody
    @RequestMapping(value = "event/getEventListByTypeAndTime", method = RequestMethod.POST)
    public ResponseResult getEventListByTypeAndTime(@RequestParam int typeId,
                                                    @RequestParam Date startTime,
                                                    @RequestParam Date endTime,
                                                    HttpSession httpSession) throws Exception{
        //        int userId = 2;
//        String date = "2019-05-11 00:00:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        startTime = sdf.parse(date);
//        endTime = new Date(startTime.getTime() + 24 * 60 * 60 * 1000 * 7);

        List<EventVO> eventVOs = eventService.getEventVOsByTypeAndTime(startTime,endTime, typeId);
        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("EventVOs",eventVOs);
        data.put("Total",eventVOs.size());
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","按类型获取日程成功"));
        return responseResult;

    }
}
