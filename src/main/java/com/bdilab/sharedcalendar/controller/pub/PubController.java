package com.bdilab.sharedcalendar.controller.pub;

import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.User;

import com.bdilab.sharedcalendar.service.pub.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//AppID:AppID
//AppSecret:39139bf387a9f8cb0bedd82d4a8af569

/**
 * Created by hh on 2019/3/29
 * 当前控制器类主要为用户的登录功能使用
 * 对于signCheck
 * 1.用户已经注册过，"meta":{"success":true,"code":"001","message":"登录成功"}
 * 2.用户为首次登录，"meta":{"success":false,"code":"002","message":"用户为首次登录，需要注册"}
 * 对于signup
 * 1.注册成功，"meta":{"success":true,"code":"003","message":"注册成功"}
 */
@Controller
/**
 * CrossOrigin 注解，允许跨域
 */
@CrossOrigin
public class PubController {

    @Autowired
    private PubService pubService;

    /**
     * 微信服务器给开发者服务器颁发的身份凭证，开发者可以用session_key请求微信服务器其他接口来获取一些其他信息
     * session_key不应该泄露或者下发到小程序前端
     */
    private String session_key;

    /**
     * 开发者服务器和开发者的小程序之间的会话密钥，
     * 登录成功之后，开发者服务器需要生成会话密钥SessionId，在服务端保持SessionId对应的用户身份信息，同时把SessionId返回给小程序
     */
    private String session_id;

    private String open_id;

    /**
     * 登录，若是，需要输入用户名和密码进行登录；反之可以直接通过微信授权登录
     * @param rescode 微信后台生成的一张临时的身份证，有效时间为5分钟
     * @param httpSession 采用从Redis自动注入的Session
     */
    @ResponseBody
    @RequestMapping(value = "/public/signIn", method = RequestMethod.POST)
    public ResponseResult signIn(@RequestParam String rescode, HttpSession httpSession) {
        //在pubService中利用rescode调用微信api获取openid，查询数据库中是否有该id对应的用户，组装成UserModel返回
        User um = pubService.userSignIn(rescode);
        //创建session维护openid
        httpSession.setAttribute("openID", um.getUserOpenid());
        session_id = httpSession.getId();
        System.out.println("session_id = " + httpSession.getId());
        //首次登录，需要注册

        Map<String, String> data = new HashMap<>();
        //返回session_id给前端缓存
        data.put("session_id", session_id);
        return new ResponseResult(true, "001", "登录成功", data);

    }

    /**
     * 获取用户信息，用户打开小程序时授权可以获得userInfo信息，在用户登录小程序后，发送到后端作为用户数据保存
     * @param nickName 用户微信昵称
     * @param avatarUrl 用户微信头像url
     * @param httpSession 采用从Redis自动注入的Session
     */
    @ResponseBody
    @RequestMapping(value = "/public/updateUserInfo", method = RequestMethod.POST)
    public ResponseResult updateUserInfo(@RequestParam String nickName,@RequestParam String avatarUrl, HttpSession httpSession) {
        User user = new User();
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        //从session中获取open_id
        user.setUserOpenid(httpSession.getAttribute("openID").toString());
        if(pubService.updateUserInfo(user)){
            Map<String, String> data = new HashMap<>();
            //返回用户名
            data.put("userName", user.getNickName());
            return new ResponseResult(true, "001", "更新用户信息成功", data);
        }
        else return new ResponseResult(true, "002", "更新用户信息失败", null);

    }

}
