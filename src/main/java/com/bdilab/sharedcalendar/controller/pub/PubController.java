package com.bdilab.sharedcalendar.controller.pub;

import com.bdilab.sharedcalendar.common.response.ResponseResult;
import com.bdilab.sharedcalendar.model.User;

import com.bdilab.sharedcalendar.service.pub.PubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

//AppID:AppID
//AppSecret:39139bf387a9f8cb0bedd82d4a8af569

/**
 * Created by hh on 2019/3/29
 * 当前控制器类主要为用户的登录功能使用
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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
            //httpSession.setAttribute("user_id", 2);
            session_id = httpSession.getId();
            System.out.println("session_id = " + httpSession.getId());
            Map<String, String> data = new HashMap<>();
            //返回session_id给前端缓存
            try{
                //对session_id进行编码，不然无法解析
                BASE64Encoder encoder = new BASE64Encoder();
                session_id = encoder.encode(session_id.getBytes("UTF-8"));
                System.out.println(session_id);
            }catch (Exception e){
                e.printStackTrace();
            }
            data.put("session_id", session_id);
            return new ResponseResult(true, "001", "登录成功", data);
        }
        return new ResponseResult(false, "002", "登陆失败，rescode无效", null);

//            httpSession.setAttribute("user_id", 2);
//            //httpSession.setAttribute("user_id", 2);
//            session_id = httpSession.getId();
//            System.out.println("session_id = " + httpSession.getId());
//            Map<String, String> data = new HashMap<>();
//            //返回session_id给前端缓存
//            try{
//                //对session_id进行编码，不然无法解析
//                BASE64Encoder encoder = new BASE64Encoder();
//                session_id = encoder.encode(session_id.getBytes("UTF-8"));
//                System.out.println(session_id);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            data.put("session_id", session_id);
//            return new ResponseResult(true, "001", "登录成功", data);

    }
    /**
     * 获取用户信息，用户打开小程序时授权可以获得userInfo信息，在用户登录小程序后，发送到后端作为用户数据保存
     * @param nickName 用户微信昵称
     * @param avatarUrl 用户微信头像url
     * @param httpSession 采用从Redis自动注入的Session
     */
    @ResponseBody
    @RequestMapping(value = "/public/updateUserInfo", method = RequestMethod.POST)
    public ResponseResult updateUserInfo(@RequestParam String nickName,
                                         @RequestParam String avatarUrl,
                                         HttpSession httpSession) {
        User user = new User();
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setId(2);
        if(pubService.updateUserInfo(user)){
            Map<String, String> data = new HashMap<>();
            //返回用户名
            data.put("userName", user.getNickName());
            return new ResponseResult(true, "001", "更新用户信息成功", data);
        }
        else return new ResponseResult(true, "002", "更新用户信息失败", null);
    }

    /**
     * 获取多个formID并缓存到redis
     */
    @ResponseBody
    @RequestMapping(value = "/public/getFormIdList", method = RequestMethod.POST)
    public ResponseResult getFormIdList(@RequestParam List<String> formIds,
                                        HttpSession httpSession) {
        int userId = Integer.parseInt(httpSession.getAttribute("user_id").toString());
        //设置key值，模式为【openID-时间】
        String key = pubService.getUserInfo(userId).getUserOpenid() +"-"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(formIds.size()+" formIDs in total");
        for (String formId:formIds
        ) {
            System.out.println("formId: "+formId);
            stringRedisTemplate.opsForSet().add(key,formId);
        }
        //设置formID过期时间为7天
        stringRedisTemplate.expire(key,7 , TimeUnit.DAYS);
        return new ResponseResult(true, "001", "缓存formID成功", null);
    }
}
