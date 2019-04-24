package com.bdilab.sharedcalendar.controller.event;

import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.service.event.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;

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
                                      @RequestParam(value = "repeatEndTime") Date repeatEndTime,
                                      @RequestParam(value = "repeatTimes") Integer repeatTimes,
                                      @RequestParam(value = "noticeChoice") Integer noticeChoice,
                                      @RequestParam(value = "eventContent") String eventContent,
                                      HttpSession httpSession) throws Exception{
        ResponseResult responseResult = new ResponseResult();
        Event event = new Event();
        try{
            Integer creatorId = (Integer) httpSession.getAttribute("openID");
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
                responseResult.setSuccess(true);
                responseResult.setCode("301");
                responseResult.setMessage("添加日程成功");
            }
        }catch (Exception e){
            responseResult.setSuccess(false);
            responseResult.setCode("302");
            responseResult.setMessage("添加日程失败");
        }
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
                                      @RequestParam(value = "eventName") String eventName,
                                      @RequestParam(value = "startTime")Date startTime,
                                      @RequestParam(value = "endTime") Date endTime,
                                      @RequestParam(value = "fkTypeId") Integer fkTypeId,
                                      @RequestParam(value = "eventFrequency") Integer eventFrequency,
                                      @RequestParam(value = "eventEndCondition") Integer eventEndCondition,
                                      @RequestParam(value = "repeatEndTime") Date repeatEndTime,
                                      @RequestParam(value = "repeatTimes") Integer repeatTimes,
                                      @RequestParam(value = "noticeChoice") Integer noticeChoice,
                                      @RequestParam(value = "eventContent") String eventContent,
                                      HttpSession httpSession) throws Exception{
        ResponseResult responseResult = new ResponseResult();
        //Integer creatorId = (Integer) httpSession.getAttribute("openID");
        Event event = eventService.selectEventById(eventId);
        event.setEventName(eventName);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setFkTypeId(fkTypeId);
        event.setEventFrequency(eventFrequency);
        event.setNoticeChoice(noticeChoice);
        event.setEventContent(eventContent);
        event.setEventEndCondition(eventEndCondition);
        //重复终止条件为0设置重复次数，重复终止条件为1设置重复截止时间，否则不设置
        if(eventEndCondition == 0){
            event.setRepeatTimes(repeatTimes);
        }else if (eventEndCondition == 1){
            event.setRepeatEndTime(repeatEndTime);
        }else {

        }
        if(eventService.upadateEvent(event)){
            responseResult.setSuccess(true);
            responseResult.setCode("401");
            responseResult.setMessage("更新日程成功");
        }else {
            responseResult.setSuccess(false);
            responseResult.setCode("402");
            responseResult.setMessage("更新日程失败");
        }
        return responseResult;
    }

    /**
     * 删除日程
     */
    @ResponseBody
    @RequestMapping(value = "event/deleteEvent", method = RequestMethod.POST)
    public ResponseResult deleteEvent(@RequestParam("eventId") Integer eventId) throws Exception{
        ResponseResult responseResult = new ResponseResult();
        if (eventService.deleteEvent(eventId)){
            responseResult.setSuccess(true);
            responseResult.setCode("501");
            responseResult.setMessage("删除日程成功");
        }else {
            responseResult.setSuccess(false);
            responseResult.setCode("502");
            responseResult.setMessage("删除日程失败");
        }
        return responseResult;
    }

    /**
     * 获取用户某个时间段的日程列表信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @throws Exception
     */
//    @ResponseBody
//    @RequestMapping(value = "event/getEventListByTime", method = RequestMethod.POST)
//    public ResponseResult getEvents(@RequestParam Date startTime,
//                                    @RequestParam Date endTime) throws Exception{
//
//    }
}
