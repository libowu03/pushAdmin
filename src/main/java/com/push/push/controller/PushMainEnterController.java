package com.push.push.controller;

import com.google.gson.Gson;
import com.push.push.bean.MessageBean;
import com.push.push.utils.WebSocketUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PushMainEnterController {


    @ResponseBody()
    @RequestMapping(value = "/send",method = RequestMethod.POST  , produces = "application/json;charset=UTF-8")
    public String home(@RequestBody MessageBean messageBean){
            Gson gson = new Gson();
            if (messageBean != null){
                String backInfo = gson.toJson(messageBean);
                WebSocketUtils.sendMessageToUser(backInfo);
            }else {
                WebSocketUtils.sendMessageToUser("{}");
            }
            return "{}";
    }



    @ResponseBody
    @RequestMapping(value="/")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("starter");
        return mv;
    }

}
