package com.xianbei.pocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianbei.pocket.pojo.JenkinJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PocketCiApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(PocketCiApplicationTests.class);
	@Autowired
	private JenkinJob jenkinJob;
	@Test
	public void contextLoads() {

		List<Map<String, String>> jobs = jenkinJob.getJobs();
		ObjectMapper om = new ObjectMapper();
		try {
			System.out.println("jenkins_job.yml【" + om.writeValueAsString(jobs) + "】");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
