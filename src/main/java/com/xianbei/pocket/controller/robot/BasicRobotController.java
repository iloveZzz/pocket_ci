package com.xianbei.pocket.controller.robot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Controller
@RequestMapping("/robot")
public class BasicRobotController extends AbstractRobotController{
    @RequestMapping("/ding")
    public void home(String key) {
    }
}


