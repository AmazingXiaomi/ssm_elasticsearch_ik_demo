package com.xiaomi.controller;

import com.xiaomi.bean.Usermsg;
import com.xiaomi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaomi
 * @Description:
 * @Date:下午 1:48 2018/7/10 0010
 * @Email:a1205938863@gmail.com
 **/
@Controller
@RequestMapping("user")
public class UserMsgController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "createIndex")
    @ResponseBody
    public  String createIndex(){
        Map map=new HashMap(10);
        map.put("id",true);
        map.put("reason",false);
        map.put("companytype",true);
        map.put("actiontype",true);
        map.put("actiontime",false);
        int index = userService.createIndex(map);
        if (index>0){
            return "Success";
        }
        return "failed";
    }

    @RequestMapping(value = "query")
    @ResponseBody
    public  Usermsg query(){
        Usermsg u=new Usermsg();
        Usermsg enter = userService.getEnter(u);
        return enter;
    }

    @RequestMapping(value = "queryList")
    @ResponseBody
    public  List<Usermsg> queryList(String reason){
        List<Usermsg> usermsgs = userService.queryList(reason);
        return usermsgs;
    }


}
