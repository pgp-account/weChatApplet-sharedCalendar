package com.bdilab.sharedcalendar.service.pub.impl;

import com.bdilab.sharedcalendar.mapper.EventTypeMapper;
import com.bdilab.sharedcalendar.mapper.PubMapper;
import com.bdilab.sharedcalendar.model.EventType;
import com.bdilab.sharedcalendar.model.User;
import com.bdilab.sharedcalendar.service.pub.PubService;
import com.bdilab.sharedcalendar.statics.AppInfo;
import com.bdilab.sharedcalendar.utils.HttpRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PubServiceImpl implements PubService {

    @Autowired
    private PubMapper pubMapper;
    @Autowired
    private EventTypeMapper eventTypeMapper;
    /**
     * 登录，通过微信授权登录，使用前端发送的rescode与微信后台交互获得openid
     * 若用户为首次登陆，在数据库中添加用户信息
     * @param rescode 微信临时身份证
     * */
    @Override
    public User userSignIn(String rescode) {
        String openId;
        String appId = AppInfo.getAppID();
        // 小程序的 app secret (在微信小程序管理后台获取)
        String secretId = AppInfo.getAppSecret();
        // 授权（必填）
        String grant_type = "authorization_code";

        // 请求参数
        String params = "appid=" + appId + "&secret=" + secretId + "&js_code=" + rescode + "&grant_type=" + grant_type;
        // 发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        // 解析相应内容（转换成json对象）
        JSONObject json = new JSONObject(sr);
        // 获取会话密钥（session_key）
        //TODO:持久化session_key
        if(json.has("session_key")){
            String session_key = json.get("session_key").toString();
            // 用户的唯一标识（openid）
            openId = (String) json.get("openid");
            User um = pubMapper.selectUserByOpenid(openId);
            //数据库中没有该用户，则添加该用户，并为用户新建一个默认类型
            if(um == null){
                um = new User();
                um.setUserOpenid(openId);
                //insert后自动将主键赋值给um.userId
                pubMapper.insertUser(um);

                //为用户新建一个默认日程类型
                EventType eventType = new EventType();
                eventType.setTypeName("默认");
                eventType.setTypeTransparency(1);
                eventType.setSubscriberNum(0);
                eventType.setFkCreatorId(um.getId());
                eventType.setSubscriberNum(0);
                eventType.setCreateTime(new Date());
                eventTypeMapper.insertEventType(eventType);

                eventType.setCreateTime(new Date());
            }
            return um;
        }
        return null;
    }

}
