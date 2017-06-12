package com.xianbei.pocket.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.xianbei.pocket.service.JenkinsService;
import com.xianbei.pocket.pojo.JenkinJob;
import com.xianbei.pocket.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Service("jenkinsService")
public class JenkinsServiceImpl implements JenkinsService {
    private static final Logger LOG = LoggerFactory.getLogger(JenkinsServiceImpl.class);
    @Autowired
    private JenkinJob jenkinJob;

    @Override
    public void triggerBuild(String hook_body) {
        try {
            Map job_map = null;
            String actor= JsonPath.read(hook_body,"$.repository.name");
            String repo_name= JsonPath.read(hook_body,"$.repository.name");
            String repo_full_name= JsonPath.read(hook_body,"$.repository.full_name");
            String path_branch= JsonPath.read(hook_body,"$.push.changes..new.name").toString();
            String message= JsonPath.read(hook_body,"$.push.changes..new.target.message").toString();
            String compare_html= JsonPath.read(hook_body,"$.push.changes..new.target.links.html.href").toString();
            for (Map<String,String> job_m : jenkinJob.getJobs()) {
                if (path_branch.contains(job_m.get("git_branch"))) {
                    job_map = job_m;
                }
            }
            if (job_map != null) {
                String jenkinsUrl = jenkinJob.getJenkins_host()
                        +"/job/" + job_map.get("job_name")
                        + "/" + job_map.get("job_type")
                        + "?token=" + job_map.get("job_token")
                        + "&" + job_map.get("job_param");
                LOG.info("执行ci的url【"+jenkinsUrl+"】");
                LOG.info("bitbucket仓库名称【"+repo_name+"】");
                LOG.info("bitbucket仓库全名称【"+repo_full_name+"】");
                LOG.info("bitbucket作者【"+actor+"】");
                LOG.info("bitbucket分支【"+path_branch+"】");
                LOG.info("bitbucket提交消息【"+message+"】");
                LOG.info("bitbucket比较代码url【"+compare_html+"】");
                String rep_body= HttpUtil.getResponseBody(HttpUtil.createConnection(jenkinsUrl));
                System.out.println(rep_body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
