package com.push.push.controller;

import com.google.gson.Gson;
import com.push.push.bean.MessageBean;
import com.push.push.webSocket.WebSocketServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PushMainEnterController {


    @ResponseBody()
    @RequestMapping(value = "/send",method = RequestMethod.POST  , produces = "application/json;charset=UTF-8")
    public String home( @RequestBody MessageBean messageBean){
        try {
            Gson gson = new Gson();
            if (messageBean != null){
                String backInfo = gson.toJson(messageBean);
                WebSocketServer.sendInfo(backInfo);
            }else {
                WebSocketServer.sendInfo("{}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";
    }



    @ResponseBody
    @RequestMapping(value="/")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("starter");
        mv.addObject("title","欢迎使用Thymeleaf!");
        try {
            WebSocketServer.sendInfo("你好");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }

}
