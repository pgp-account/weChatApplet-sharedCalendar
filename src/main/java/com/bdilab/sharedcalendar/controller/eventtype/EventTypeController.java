package com.bdilab.sharedcalendar.controller.eventtype;

import com.bdilab.sharedcalendar.common.response.MetaData;
import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.Event;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.UuidRelation;
import com.bdilab.sharedcalendar.service.eventtype.EventTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hh on 2019/4/1
 * 当前控制器类主要为日程类型相关功能
 */
@Controller
/**
 * CrossOrigin 注解，允许跨域
 */
@CrossOrigin
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
            return new ResponseResult(true, "001", "创建成功", data);
        return new ResponseResult(false, "002", "创建失败", data);
    }

    /**
     * 获取用户日程类型列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/eventtype/getEventTypeList", method = RequestMethod.GET)
    public ResponseResult createEventTpye(HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        List<EventType> eventTypes = eventTypeService.getEventTypeList(userId);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(eventTypes);
        responseResult.setMeta(new MetaData( true,"001","获取用户所有日程类型列表成功"));
        return responseResult;
    }



}
