package com.bdilab.sharedcalendar.controller.uuid;

import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.UuidRelation;
import com.bdilab.sharedcalendar.service.uuid.UuidRelationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
@Api(value = "uuid relation controller")
public class UuidRelationController {
    @Autowired
    UuidRelationService uuidRelationService;

    /**
     * 获取日程类型分享链接
     * @param typeId 类型id
     * @param httpSession
     */
    @ResponseBody
    @RequestMapping(value = "/uuid/generateShareCode", method = RequestMethod.POST)
    public ResponseResult generateShareCode(@RequestParam int typeId, HttpSession httpSession) {
        UuidRelation uuidRelation = uuidRelationService.generateShareCode(typeId);
        if(uuidRelation!=null) {
            Map<String, String> data = new HashMap<>();
            data.put("share_code",uuidRelation.getUuid());
            return new ResponseResult(true,"001","获取日程类型分享链接成功",data);
        }
        return new ResponseResult(false,"002","获取日程类型分享链接失败",null);
    }

    /**
     * 订阅日程类型
     * 当该日程类型已被删除，需提醒用户
     * @param shareCode uuid
     * @param httpSession
     */
    @ResponseBody
    @RequestMapping(value = "/uuid/subscribeEventType", method = RequestMethod.POST)
    public ResponseResult subscribeEventType(@RequestParam String shareCode,
                                             HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //int userId = 2;
        int typeId = uuidRelationService.getShareCodeStatus(shareCode,userId);
        if(uuidRelationService.isMine(userId,typeId)) return new ResponseResult(false,"002","订阅失败，不可以订阅自己创建的类型哦",null);
        if(typeId == 0) return new ResponseResult(false,"002","订阅失败，该分享链接已被使用或已过期，或您已订阅过该类型",null);
        else{

            if(uuidRelationService.subscribeEventType(shareCode,userId,typeId))
                return new ResponseResult(true,"001","订阅成功",null);
        }
        return new ResponseResult(false,"003","订阅失败",null);
    }

    /**
     * 取消订阅
     * @param typeId 类型id
     */
    @ResponseBody
    @RequestMapping(value = "/uuid/cancelSubscribe", method = RequestMethod.POST)
    public ResponseResult subscribeEventType(@RequestParam int typeId,
                                             HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //int userId = 2;
        if(uuidRelationService.cancelSubscribe(userId,typeId))
            return new ResponseResult(true,"001","取消订阅成功",null);
        return new ResponseResult(false,"002","取消订阅失败",null);
    }
}
