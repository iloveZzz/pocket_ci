package com.xianbei.pocket.controller;

import com.xianbei.pocket.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Controller
public class BitbucketController {
    @Autowired
    private JenkinsService jenkinsService;

    @RequestMapping("pocket_hook")
    @ResponseBody
    public String pocket_hook(String rq, BindingResult bindingResult) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (bindingResult.hasErrors()) {
            map.put("errorCode", "000001");
            map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
        }
        jenkinsService.triggerBuild(rq);
        return "success";
    }
}
