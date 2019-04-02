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
     */
    @ResponseBody
    @RequestMapping(value = "/uuid/getShareCode", method = RequestMethod.GET)
    public ResponseResult getShareUrl(@RequestParam int typeId, HttpSession httpSession) {
        //EventType eventType = eventTypeService.getEventTypeById(typeId);
        UuidRelation uuidRelation = uuidRelationService.generateShareCode(typeId);
        if(uuidRelation!=null) {
            Map<String, String> data = new HashMap<>();
            data.put("share_code",uuidRelation.getUuid());
            return new ResponseResult(true,"001","获取日程类型分享链接成功",data);
        }
        return new ResponseResult(false,"002","获取日程类型分享链接失败",null);
    }
}
