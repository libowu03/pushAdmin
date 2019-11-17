package com.push.push.controller;

import com.google.gson.Gson;
import com.push.push.bean.MessageBean;
import com.push.push.bean.SendMessage;
import com.push.push.utils.WebSocketUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PushMainEnterController {


    /**
     * 群发消息
     * @param messageBean
     * @return
     */
    @ResponseBody()
    @RequestMapping(value = "/sendAll",method = RequestMethod.POST  , produces = "application/json;charset=UTF-8")
    public String sendAll(@RequestBody MessageBean messageBean){
            Gson gson = new Gson();
            if (messageBean != null){
                String backInfo = gson.toJson(messageBean);
                WebSocketUtils.sendMessageToUser(backInfo);
            }else {
                WebSocketUtils.sendMessageToAdmin("发送失败");
            }
            return "{}";
    }


    /**
     * 发送给单个用户
     * @param messageBean
     * @return
     */
    @ResponseBody()
    @RequestMapping(value = "/sendSingle",method = RequestMethod.POST  , produces = "application/json;charset=UTF-8")
    public String sendSingle(@RequestBody MessageBean messageBean){
        Gson gson = new Gson();
        if (messageBean != null){
            String backInfo = gson.toJson(messageBean);
            WebSocketUtils.sendMessageToUserForSingle(backInfo);
        }else {
            WebSocketUtils.sendMessageToAdmin("发送失败");
        }
        return "{}";
    }

    /**
     *
     * 通过渠道发送
     * @param messageBean
     * @return
     */

    @ResponseBody()
    @RequestMapping(value = "/sendByChannel",method = RequestMethod.POST  , produces = "application/json;charset=UTF-8")
    public String sendByChannel(@RequestBody MessageBean messageBean){
        Gson gson = new Gson();
        if (messageBean != null){
            String backInfo = gson.toJson(messageBean);
            WebSocketUtils.sendMessageToUserForChannel(backInfo);
        }else {
            WebSocketUtils.sendMessageToAdmin("发送失败");
        }
        return "{}";
    }

    /**
     * 通过组合条件发送
     * @param messageBean
     * @return
     */

    @ResponseBody()
    @RequestMapping(value = "/sendByCondition",method = RequestMethod.POST  , produces = "application/json;charset=UTF-8")
    public String sendByCondition(@RequestBody MessageBean messageBean){
        Gson gson = new Gson();
        String msg;
        String status;
        if (messageBean != null){
            String backInfo = gson.toJson(messageBean);
            msg = WebSocketUtils.sendMessageToUserForCondition(backInfo);
            status = "ok";
        }else {
            WebSocketUtils.sendMessageToAdmin("发送失败");
            msg ="false";
            status = "bad";
        }
        SendMessage sendMessage = new SendMessage(status,msg);
        return gson.toJson(sendMessage);
    }


    /**
     * 连接是默认跳转到starter.html这个网页下，这个网页就是管理后台的主界面
     * @param mv
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("starter");
        return mv;
    }

}
