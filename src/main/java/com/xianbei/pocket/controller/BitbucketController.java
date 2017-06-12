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
    private static final String GITHUB="";
    private static final String BITBUCKET="";
    private static final String GITLAB="";

    @Autowired
    private JenkinsService jenkinsService;

    @RequestMapping("bitbucket_hook")
    @ResponseBody
    public String bitbucket_hook(String rq, BindingResult bindingResult) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (bindingResult.hasErrors()) {
            map.put("errorCode", "000001");
            map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
        }
        jenkinsService.triggerBuildByBitbucket(rq);
        return "success";
    }
    @RequestMapping("github_hook")
    @ResponseBody
    public String github_hook(String rq, BindingResult bindingResult) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (bindingResult.hasErrors()) {
            map.put("errorCode", "000001");
            map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
        }
        jenkinsService.triggerBuildByGithub(rq);
        return "success";
    }
}
