package com.bdilab.sharedcalendar.controller.eventtype;

import com.bdilab.sharedcalendar.common.response.MetaData;
import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import com.bdilab.sharedcalendar.service.eventtype.EventTypeService;
import com.bdilab.sharedcalendar.vo.EventTypeInfoVO;
import com.bdilab.sharedcalendar.vo.SubEventTypeVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by hh on 2019/4/1
 * 当前控制器类主要为日程类型相关功能
 */
@Controller
/**
 * CrossOrigin 注解，允许跨域
 */
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
@Api(value = "envent type controller")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;
    /**
     * 创建新类型
     * @param typeName 类型名称
     * @param typeDescrption 类型描述
     * @param typeTransparency 类型透明度，0-仅自己可见；1-分享可见
     * @param createTime 创建时间
     * @param httpSession 采用从Redis自动注入的Session
     */

    @ResponseBody
    @RequestMapping(value = "/eventtype/createEventTpye", method = RequestMethod.POST)
    public ResponseResult createEventTpye(@RequestParam String typeName,
                                           @RequestParam(required = false) String typeDescrption,
                                           @RequestParam int typeTransparency,
                                           @RequestParam Date createTime,
                                           HttpSession httpSession) {
        EventType eventType = new EventType();
        eventType.setTypeName(typeName);
        eventType.setTypeDescrption(typeDescrption);
        eventType.setTypeTransparency(typeTransparency);
        eventType.setSubscriberNum(0);
        eventType.setFkCreatorId(Integer.parseInt(httpSession.getAttribute("user_id").toString()));
        eventType.setCreateTime(createTime);

        Map<String, String> data = new HashMap<>();
        data.put("type_name",typeName);

        if(eventTypeService.createEventType(eventType))
            return new ResponseResult(true, "001", "创建类型成功", data);
        return new ResponseResult(false, "002", "创建类型失败", data);
    }

    /**
     * 获取用户自己创建的日程类型列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/getEventTypeList", method = RequestMethod.GET)
    public ResponseResult getEventTypeList(HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //int userId = 3;
        List<EventType> eventTypes = eventTypeService.getEventTypeList(userId);
        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("EventTypes",eventTypes);
        data.put("Total",eventTypes.size());
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","获取用户所有日程类型列表成功"));
        return responseResult;
    }

    /**
     * 获取用户订阅的日程类型列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/getSubEventTypeList", method = RequestMethod.GET)
    public ResponseResult getSubEventTypeList(HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //int userId = 2;
        List<SubEventTypeVO> subEventTypeVOS = eventTypeService.getEventSubTypeList(userId);
        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("SubscribedEventTypes",subEventTypeVOS);
        data.put("Total",subEventTypeVOS.size());
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","获取用户订阅日程类型列表成功"));
        return responseResult;
    }

    /**
     * 获取用户所有的日程类型列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/getAllEventTypeList", method = RequestMethod.GET)
    public ResponseResult getAllEventTypeList(HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //int userId = 2;
        List<SubEventTypeVO> subEventTypeVOS = eventTypeService.getEventSubTypeList(userId);
        List<EventType> eventTypes = eventTypeService.getEventTypeList(userId);

        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("Created Event Types",eventTypes);
        data.put("CreatedEventTypes Total",eventTypes.size());
        data.put("Subscribed Event Types",subEventTypeVOS);
        data.put("SubscribedEventTypes Total",subEventTypeVOS.size());
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","获取用户所有日程类型列表成功"));
        return responseResult;
    }

    /**
     * 获取特定日程类型详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/getEventTypeInfo", method = RequestMethod.GET)
    public ResponseResult getEventTypeInfo(@RequestParam int typeId, HttpSession httpSession) {
        EventTypeInfoVO eventTypeInfoVO =  eventTypeService.getEventTypeInfoById(typeId);
        ResponseResult responseResult = new ResponseResult();
        Map<String ,Object> data = new HashMap<>();
        data.put("EventType",eventTypeInfoVO);
        responseResult.setData(data);
        responseResult.setMeta(new MetaData( true,"001","获取日程类型详细信息成功"));
        return responseResult;
    }

    /**
     * 用户更新日程类型信息
     * 现在的逻辑下，一个能见度为“分享可见”的类型，
     * 被几个人订阅后，如果创建者修改了它的能见度为“仅自己可见”，会出现一些问题：
     * 1、已经订阅该类型的用户能不能继续订阅
     * 2、已经订阅该类型的能不能分享该类型
     * 对于问题1，我们给用户提供这个选择
     * （1）不让这些用户继续订阅
     * （2）已订阅的可以继续订阅
     * 对于问题2，已经订阅该类型的应当不能分享该类型
     * 用户分享类型时，无论是否是类型的创建者，都需要根据透明度来判断是否能进行分享。
     * @param typeId 类型id
     * @param typeName 类型名
     * @param typeDescrption 类型描述
     * @param typeTransparency 类型可视度
     * @param operationType 操作类型，当将typeTransparency由1为0时，需要该参数：
     *                          1、不让这些用户继续订阅
     *                          2、已订阅的可以继续订阅
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/updateEventTypeInfo", method = RequestMethod.POST)
    public ResponseResult updateEventTypeInfo(@RequestParam int typeId,
                                              @RequestParam(required = false) String typeName,
                                              @RequestParam(required = false) String typeDescrption,
                                              @RequestParam(required = false) Integer typeTransparency,
                                              @RequestParam(required = false)Integer operationType) {
        EventType eventType = new EventType();
        eventType.setId(typeId);
        eventType.setTypeName(typeName);
        eventType.setTypeTransparency(typeTransparency);
        eventType.setTypeDescrption(typeDescrption);
        if(typeTransparency!=null){
            if(typeTransparency==0){
                if(eventTypeService.updateEventTypeInfo(eventType,operationType))
                    return new ResponseResult(false,"001","更新成功");
                return new ResponseResult(false,"001","更新失败");
            }
        }
        if(eventTypeService.updateEventTypeInfo(eventType,null))
            return new ResponseResult(false,"001","更新成功");
        return new ResponseResult(false,"001","更新失败");
    }

    /**
     * 删除日程类型
     * 删除日程类型时，该日程类型下的所有日程有两种操作方式：
     * 1、一并删除
     * 2、转至另一个日程类型下
     * @param eventTypeIds 用","隔开的多个EventTypeId
     * @param operationType 对日程类型下的日程的操作方式
     * @param newEventType 当将日程转至另一日程类型下时，对应日程类型的id
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/deleteEventTypes", method = RequestMethod.POST)
    public ResponseResult deleteEventTypes(@RequestParam String eventTypeIds,
                                           @RequestParam int operationType,
                                           @RequestParam(required = false,defaultValue = "0") int newEventType) {
        System.out.println("eventTypeIds="+eventTypeIds);
        String[] messageIds=eventTypeIds.split(",");
        if(messageIds.length==0){
            return new ResponseResult(false,"002","参数错误，日程类型id为空");
        }

        List<Integer> list=new ArrayList<>();
        for(int i=0;i<messageIds.length;i++){
            list.add(Integer.valueOf(messageIds[i]));
        }
        if(operationType==1){
            eventTypeService.deleteEventTypes(list);
        }
        else{
            eventTypeService.deleteEventTypes(list,newEventType);
        }

        return new ResponseResult(true,"001","删除日程类型成功",null);
    }
}
