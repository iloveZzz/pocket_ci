package com.xianbei.pocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianbei.pocket.controller.BitbucketController;
import com.xianbei.pocket.pojo.JenkinJob;
import com.xianbei.pocket.service.JenkinsService;
import com.xianbei.pocket.utils.ApplicationPropertiesBindingPostProcessor;
import com.xianbei.pocket.utils.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PocketCiApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(PocketCiApplicationTests.class);
	@Autowired
	private BitbucketController bitbucketController;
	@Autowired
	private JenkinJob jenkinJob;
	@Autowired
	private JenkinsService jenkinsService;
	@Test
	public void contextLoads() throws JsonProcessingException {
		JenkinJob cc = (JenkinJob)ApplicationPropertiesBindingPostProcessor.bindPropertiesToTarget(JenkinJob.class);
		ObjectMapper om = new ObjectMapper();
		System.out.println("jenkins_job.yml【" + om.writeValueAsString(cc) + "】");
	}

}
