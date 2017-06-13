package com.xianbei.pocket.pojo;

import com.xianbei.pocket.utils.ApplicationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@ApplicationProperties(prefix = "jenkins_jobs",locations = "file:config/jenkins_job.yml")
public class JenkinJob {
    private List<Map<String, String>> jobs = new ArrayList<>();
    private String jenkins_host;

    public List<Map<String, String>> getJobs() {
        return jobs;
    }

    public void setJobs(List<Map<String, String>> jobs) {
        this.jobs = jobs;
    }

    public String getJenkins_host() {
        return jenkins_host;
    }

    public void setJenkins_host(String jenkins_host) {
        this.jenkins_host = jenkins_host;
    }
}
