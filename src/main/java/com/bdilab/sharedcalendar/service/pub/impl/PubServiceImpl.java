package com.bdilab.sharedcalendar.service.pub.impl;

import com.bdilab.sharedcalendar.mapper.PubMapper;
import com.bdilab.sharedcalendar.model.User;
import com.bdilab.sharedcalendar.service.pub.PubService;
import com.bdilab.sharedcalendar.statics.AppInfo;
import com.bdilab.sharedcalendar.utils.HttpRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubServiceImpl implements PubService {
    private PubMapper pubMapper;
    /**
     * 验证是否为首次登录，根据rescode获取用户openid，并与数据库做比对
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
        String session_key = json.get("session_key").toString();
        // 用户的唯一标识（openid）
        openId = (String) json.get("openid");

        User um = pubMapper.selectUserByOpenId(openId);
        if(um == null)
            um = new User();
            um.setUserOpenid(openId);
        pubMapper.insertUser(um);
        return um;
    }

    public boolean updateUserInfo(User user){
        if(pubMapper.updateUserInfo(user)==1)
            return true;
        else return false;
    }


}
