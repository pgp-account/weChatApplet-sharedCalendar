package com.bdilab.sharedcalendar.common.schedule;
import com.bdilab.sharedcalendar.model.NoticeForPush;
import com.bdilab.sharedcalendar.service.eventnotice.EventNoticeService;
import com.bdilab.sharedcalendar.statics.AppInfo;
import com.bdilab.sharedcalendar.utils.HttpRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class PushSchedule implements ApplicationRunner {

    String accessToken;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    EventNoticeService eventNoticeService;

    //项目启动时执行，第一次获取access token
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 首次获取access_token");
        getToken();
    }

    //每2小时重置一次token
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void resetToken() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Reset Access Token");
        getToken();
    }

    //每分钟推送一次
    @Scheduled(cron = "0 */1 * * * ?")
    public void push() {
        String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //查询所有需要推送的内容
        List<NoticeForPush> wechatNotices= eventNoticeService.getUnreadNoticeByDate(new Date());
        int noticeNum = wechatNotices.size();
        int successPushNum = 0;
        System.out.println( curDate + " Start Pushing "+noticeNum+" wechat notices ...");
        //推送
        for (NoticeForPush wechatNotice:wechatNotices
        ) {
            String formID = getFormID(wechatNotice.getOpenId());
            if(formID!=null){
                //推送
                JSONObject pushResult = pushOneNotice(wechatNotice.getOpenId(), AppInfo.getTemplateId(), "pages/index/index", formID, formData(wechatNotice.getNameAndType(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(wechatNotice.getDate()),wechatNotice.getDescription()));
                //推送成功
                if(pushResult.get("errcode").toString().equals("0")){
                    //推送后将isNoticed设为1，并根据重复条件创建下一次的提醒
                    System.out.println(curDate +" Pushed 1 notice. Notice title is "+wechatNotice.getNameAndType());
                    eventNoticeService.setNoticeRead(wechatNotice.getNoticeId());
                    successPushNum++;
                }
                else System.out.println(curDate + pushResult.get(" errcode") + ": " + pushResult.get("errmsg"));
            }
            else System.out.println(curDate +" formID of this openID has been used out.");
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Finish Pushing. Pushed " + successPushNum + " notices in total.");
    }

    private String getFormID(String openID){
        //模糊匹配取得未过期的openid列表
        Set<String> keySet;
        String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        keySet = stringRedisTemplate.keys(openID+"*");
        if(keySet==null) return null;
        Iterator<String> keyIterator = keySet.iterator();
        String formID;
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            //从redis中取出一条formId用作推送
            formID=stringRedisTemplate.opsForSet().randomMember(key);
            if(formID!=null){
                System.out.println(curDate+" Get formID from Redis. FormID = " + formID);
                //将formID从redis缓存中删除
                stringRedisTemplate.opsForSet().remove(key,formID);
                System.out.println(curDate+" Delete formID in Redis. FormID = " + formID);
                return formID;
            }
            //删除已为空集合的key值
            else stringRedisTemplate.delete(key);
        }
        //没有该openID对应的key
        return null;
    }

    //获取token
    private void getToken() {
        String appId = AppInfo.getAppID();
        String secretId = AppInfo.getAppSecret();
        // 请求参数
        String params = "grant_type=" + "client_credential" + "&appid=" + appId + "&secret=" + secretId;
        // 发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        // 解析相应内容（转换成json对象）
        JSONObject result = new JSONObject(sr);
        // 获取access_token
        if (result.has("access_token")) {
            this.accessToken = result.get("access_token").toString();
            System.out.println("access_token :" +result.get("access_token").toString());
        } else System.out.println("Error  " + result.get("errcode") + ":" + result.get("errmsg"));
    }

    /**
     * 统一服务消息
     * 小程序模板消息,发送服务通知
     *
     * @param touser           用户openid，可以是小程序的openid，也可以是公众号的openid
     * @param template_id      小程序模板消息模板id
     * @param page             小程序页面路径
     * @param formid           小程序模板消息formid
     * @param data             data
     * @return
     * @author HGL
     */
    private JSONObject pushOneNotice(String touser, String template_id,String page, String formid, JSONObject data) {
        JSONObject obj = new JSONObject();
        JSONObject weapp_template_msg = new JSONObject();
        JSONObject result = new JSONObject();
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
            obj.put("touser", touser);
            weapp_template_msg.put("template_id", template_id);
            weapp_template_msg.put("page", page);
            weapp_template_msg.put("form_id", formid);
            weapp_template_msg.put("data", data);
            weapp_template_msg.put("emphasis_keyword", data.getJSONObject("keyword1").getString("value"));
            obj.put("weapp_template_msg", weapp_template_msg);
            result = HttpRequest.Post(url, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private JSONObject formData(String value1, String value2,String value3){
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value",value1);

        JSONObject keyword2 = new JSONObject();
        keyword2.put("value",value2);

        JSONObject keyword3 = new JSONObject();
        keyword3.put("value",value3);

        JSONObject data = new JSONObject();
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("keyword3",keyword3);

        return data;
    }

}