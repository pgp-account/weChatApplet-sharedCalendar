package com.bdilab.sharedcalendar.controller.pub;

import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.User;
import com.bdilab.sharedcalendar.model.UserModel;

import com.bdilab.sharedcalendar.service.pub.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//AppID:AppID
//AppSecret:39139bf387a9f8cb0bedd82d4a8af569

/**
 * Created by Administrator on 2017/11/15.
 * 当前控制器类主要为用户的登录功能使用
 * 1.鉴定用户登录成功，返回：｛"data":"../public/structure","meta":{"success":true,"code":"001","message":"登录成功"}｝
 * 2.如果用户登录失败
 * 1）用户名不存在，则返回｛"data":[],"meta":{"success":false,"code":"002","message":"用户名不存在"}｝
 * 2）用户名存在，密码错误，则返回：｛"data":[],"meta":{"success":false,"code":"003","message":"密码错误"}｝
 * 3.如果参数检查不合法，则返回：｛"data":[],"meta":{"success":false,"code":"004","message":"参数不合法"}｝
 * 4.获取当前页面有哪些url可用，则返回：{"data":[],"meta":{"success":true,"code":"005","message":"获取用户页面可用url成功"}}
 */
@Controller
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
     * 判断用户是否是首次登录，若是，需要输入用户名和密码进行登录；反之可以直接通过微信授权登录
     * @param rescode
     * @param httpSession 采用从Redis自动注入的Session
     */
    @ResponseBody
    @RequestMapping(value = "/public/signin", method = RequestMethod.GET)
    public ResponseResult signCheck(String rescode, HttpSession httpSession) {
        //在pubService中利用rescode调用微信api获取openid，查询数据库中是否有该id对应的用户，组装成UserModel返回
        User um = pubService.userSignCheck(rescode);
        //创建session维护openid
        httpSession.setAttribute("openID", um.getUserOpenid());
        session_id = httpSession.getId();
        System.out.println("session_id = " + httpSession.getId());
        //首次登录，需要注册
        if (um.getUserName() == null) return new ResponseResult(false, "001", "用户为首次登录，需要注册", null);
        //非首次登录，进行登录操作
        else{
            Map<String, String> data = new HashMap<String, String>();
            //返回用户名和登录session_id
            data.put("userName", um.getUserName() + "");
            data.put("session_id", session_id);

            return new ResponseResult(true, "002", "登录成功", data);
        }
    }

    /**
     * 首次登录，微信登录授权，输入小程序业务侧的用户名密码，在服务端获取到用户openid后，对其进行绑定
     * @param userName         the guid provided by user
     * @param password    the md5 password sent
     * @param httpSession 采用从Redis自动注入的Session
     */
    @ResponseBody
    @RequestMapping(value = "/public/signup", method = RequestMethod.POST)
    public ResponseResult signup(String userName, String password, HttpSession httpSession) {
        System.out.println("guid=" + userName + ",password=" + password);
        //从session中获取
        String openID = httpSession.getAttribute("openID").toString();
        //组装userModel
        User user = new User();
        user.setUserName(userName);
        user.setUserPswd(password);
        user.setUserOpenid(openID);
        //新建用户
        pubService.userSignUp(user);
        //设置session为其登录
        System.out.println("session_id=" + httpSession.getId());
        httpSession.setAttribute("openID", user.getUserOpenid());
        session_id = httpSession.getId();
        Map<String, String> data = new HashMap<String, String>();
        //返回用户名和登录session_id
        data.put("userName", user.getUserName() + "");
        data.put("session_id", session_id);

        return new ResponseResult(true, "001", "登录成功", data);
    }
}
