package com.bdilab.sharedcalendar.controller.pub;

import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.User;

import com.bdilab.sharedcalendar.service.pub.PubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Api(value = "pubcontroller")
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
     * 登录，通过微信授权登录，若用户为首次登陆，在数据库中添加用户信息
     * @param rescode 微信后台生成的一张临时的身份证，有效时间为5分钟
     * @param httpSession 采用从Redis自动注入的Session
     */

    @ResponseBody
    @RequestMapping(value = "/public/signIn", method = RequestMethod.POST)
    public ResponseResult signIn(@RequestParam String rescode, HttpSession httpSession) {
        //在pubService中利用rescode调用微信api获取openid，查询数据库中是否有该id对应的用户，组装成UserModel返回
        User um = pubService.userSignIn(rescode);
        //创建session维护openid
        if(um!=null){
            httpSession.setAttribute("user_id", um.getId());
        session_id = httpSession.getId();
        System.out.println("session_id = " + httpSession.getId());
        Map<String, String> data = new HashMap<>();
        //返回session_id给前端缓存
        data.put("session_id", session_id);
        return new ResponseResult(true, "001", "登录成功", data);
        }
        return new ResponseResult(false, "002", "登陆失败，rescode无效", null);

    }

}
