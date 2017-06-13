package com.xianbei.pocket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.xianbei.pocket.pojo.CommitInfo;
import com.xianbei.pocket.service.JenkinsService;
import com.xianbei.pocket.utils.ApplicationContextUtil;
import com.xianbei.pocket.utils.Config;
import com.xianbei.pocket.utils.HttpUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Component("jenkinsService")
public class JenkinsServiceImpl implements JenkinsService {

    private static final Logger LOG = LoggerFactory.getLogger(JenkinsServiceImpl.class);

    public JenkinsServiceImpl() {

    }
    private static final String GITHUB="github";
    private static final String BITBUCKET="bitbucket";
    private static final String GITLAB="gitlab";

    public void triggerBuildByBitbucket(String hook_body) {
        try {
            Map job_map = null;
            LOG.info("------------------start-----------------");
            String actor = JsonPath.read(hook_body, "$.repository.name");
            LOG.info("bitbucket作者【" + actor + "】");
            String repo_name = JsonPath.read(hook_body, "$.repository.name");
            LOG.info("bitbucket仓库名称【" + repo_name + "】");
            String repo_full_name = JsonPath.read(hook_body, "$.repository.full_name");
            LOG.info("bitbucket仓库全名称【" + repo_full_name + "】");
            String path_branch = JsonPath.read(hook_body, "$.push.changes..new.name").toString();
            LOG.info("bitbucket分支【" + path_branch + "】");
            String message = JsonPath.read(hook_body, "$.push.changes..new.target.message").toString();
            LOG.info("bitbucket提交消息【" + message + "】");
            String compare_html = JsonPath.read(hook_body, "$.push.changes..new.target.links.html.href").toString();
            LOG.info("bitbucket比较代码url【" + compare_html + "】");
            CommitInfo commitInfo = new CommitInfo(actor,repo_name,repo_full_name,path_branch,message,compare_html);
            for (Map<String, String> job_m : Config.jenkinJob.getJobs()) {
                if (path_branch.contains(job_m.get("git_branch"))&&BITBUCKET.equals(job_m.get("git_type"))) {
                    job_map = job_m;
                }
            }
            this.sendUrl(job_map);
            LOG.info("--------------------end-------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerBuildByGithub(String hook_body) {
        try {
            Map job_map = null;
            LOG.info("------------------start-----------------");
            String actor = JsonPath.read(hook_body, "$.pusher.name");
            LOG.info("github作者【" + actor + "】");
            String repo_name = JsonPath.read(hook_body, "$.repository.name");
            LOG.info("github仓库名称【" + repo_name + "】");
            String repo_full_name = JsonPath.read(hook_body, "$.repository.full_name");
            LOG.info("github仓库全名称【" + repo_full_name + "】");
            String path_branch = JsonPath.read(hook_body, "$.repository.default_branch").toString();
            LOG.info("github分支【" + path_branch + "】");
            String message = JsonPath.read(hook_body, "$.head_commit.message").toString();
            LOG.info("github提交消息【" + message + "】");
            String compare_html = JsonPath.read(hook_body, "$.compare").toString();
            LOG.info("github比较代码url【" + compare_html + "】");

            CommitInfo commitInfo = new CommitInfo(actor,repo_name,repo_full_name,path_branch,message,compare_html);

            List<Map<String, String>> jobs = Config.jenkinJob.getJobs();
            ObjectMapper om = new ObjectMapper();
            LOG.info("jenkins_job.yml【" + om.writeValueAsString(jobs) + "】");

            for (Map<String, String> job_m : jobs) {
                if (path_branch.contains(job_m.get("git_branch"))&&GITHUB.equals(job_m.get("git_type"))) {
                    job_map = job_m;
                }
            }
            LOG.info("job_map【" + om.writeValueAsString(job_map) + "】");
            this.sendUrl(job_map);
            LOG.info("--------------------end-------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUrl(Map job_map) {
        if (job_map != null) {
            String jenkinsUrl = Config.jenkinJob.getJenkins_host()
                    + "/job/" + job_map.get("job_name")
                    + "/" + job_map.get("job_type")
                    + "?token=" + job_map.get("job_token")
                    + "&" + job_map.get("job_param");
            LOG.info("执行ci的url【" + jenkinsUrl + "】");
            String rep_body = null;
            try {
                rep_body = HttpUtil.getResponseBody(HttpUtil.createConnection(jenkinsUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            LOG.info("httpClient返回的内容【"+rep_body+"】");
        }
    }
    @PostConstruct
    public void init(){
        System.out.println(222);
    }
}
