package com.xianbei.pocket.service;

/**
 * Created by zhudaoming on 2017/6/9.
 */
public interface JenkinsService {
    public void triggerBuildByBitbucket(String hook_body);
    public void triggerBuildByGithub(String hook_body);
}
