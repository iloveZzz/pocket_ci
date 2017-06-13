package com.xianbei.pocket.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.xianbei.pocket.pojo.CommitInfo;
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
    private static final String GITHUB="github";
    private static final String BITBUCKET="bitbucket";
    private static final String GITLAB="gitlab";
    @Autowired
    private JenkinJob jenkinJob;

    @Override
    public void triggerBuildByBitbucket(String hook_body) {
        try {
            Map job_map = null;
            String actor = JsonPath.read(hook_body, "$.repository.name");
            String repo_name = JsonPath.read(hook_body, "$.repository.name");
            String repo_full_name = JsonPath.read(hook_body, "$.repository.full_name");
            String path_branch = JsonPath.read(hook_body, "$.push.changes..new.name").toString();
            String message = JsonPath.read(hook_body, "$.push.changes..new.target.message").toString();
            String compare_html = JsonPath.read(hook_body, "$.push.changes..new.target.links.html.href").toString();
            CommitInfo commitInfo = new CommitInfo(actor,repo_name,repo_full_name,path_branch,message,compare_html);
            for (Map<String, String> job_m : jenkinJob.getJobs()) {
                if (path_branch.contains(job_m.get("git_branch"))&&BITBUCKET.equals(job_m.get("git_type"))) {
                    job_map = job_m;
                }
            }
            this.sendUrl(job_map,commitInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void triggerBuildByGithub(String hook_body) {

        try {
            Map job_map = null;
            String actor = JsonPath.read(hook_body, "$.pusher.name");
            String repo_name = JsonPath.read(hook_body, "$.repository.name");
            String repo_full_name = JsonPath.read(hook_body, "$.repository.full_name");
            String path_branch = JsonPath.read(hook_body, "$.repository.default_branch").toString();
            String message = JsonPath.read(hook_body, "$.head_commit.message").toString();
            String compare_html = JsonPath.read(hook_body, "$.compare").toString();
            CommitInfo commitInfo = new CommitInfo(actor,repo_name,repo_full_name,path_branch,message,compare_html);
            for (Map<String, String> job_m : jenkinJob.getJobs()) {
                if (path_branch.contains(job_m.get("git_branch"))&&GITHUB.equals(job_m.get("git_type"))) {
                    job_map = job_m;
                }
            }
            this.sendUrl(job_map,commitInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUrl(Map job_map,CommitInfo commitInfo) {
        if (job_map != null) {
            String jenkinsUrl = jenkinJob.getJenkins_host()
                    + "/job/" + job_map.get("job_name")
                    + "/" + job_map.get("job_type")
                    + "?token=" + job_map.get("job_token")
                    + "&" + job_map.get("job_param");
            LOG.info("------------------start-----------------");
            LOG.info("执行ci的url【" + jenkinsUrl + "】");
            LOG.info("bitbucket仓库名称【" + commitInfo.getRepo_name() + "】");
            LOG.info("bitbucket仓库全名称【" + commitInfo.getRepo_full_name() + "】");
            LOG.info("bitbucket作者【" + commitInfo.getActor() + "】");
            LOG.info("bitbucket分支【" + commitInfo.getPath_branch() + "】");
            LOG.info("bitbucket提交消息【" + commitInfo.getMessage() + "】");
            LOG.info("bitbucket比较代码url【" + commitInfo.getCompare_html() + "】");
            String rep_body = null;
            try {
                rep_body = HttpUtil.getResponseBody(HttpUtil.createConnection(jenkinsUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOG.info("--------------------end-------------------");
            System.out.println(rep_body);
        }
    }
}
