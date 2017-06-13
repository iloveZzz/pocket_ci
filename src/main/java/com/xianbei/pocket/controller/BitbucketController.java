package com.xianbei.pocket.controller;

import com.xianbei.pocket.service.JenkinsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(BitbucketController.class);
    @Autowired
    private JenkinsService jenkinsService;

    @RequestMapping("bitbucket_hook")
    @ResponseBody
    public String bitbucket_hook(@RequestBody String rq, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (bindingResult.hasErrors()) {
            map.put("code", "000001");
            map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
        }
        jenkinsService.triggerBuildByBitbucket(rq);
        return "success";
    }
    @RequestMapping("github_hook")
    @ResponseBody
    public Map github_hook(@RequestBody String rq, BindingResult bindingResult) {
        LOG.info("接收到来自github上的请求");
        LOG.info("请求报文【"+rq+"】");
        Map<String, Object> map = new HashMap<String, Object>();

        if (bindingResult.hasErrors()) {
            map.put("code", "000001");
            map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            map.put("code", "000000");
            map.put("errorMsg","访问成功！");
        }
        LOG.info("触发triggerBuildByGithub！！！");
        jenkinsService.triggerBuildByGithub(rq);
        return map;
    }
}
