package com.xianbei.pocket.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Component
@ConfigurationProperties(prefix = "jenkins_jobs")
@PropertySource("file:config/jenkins_job.yml")
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
