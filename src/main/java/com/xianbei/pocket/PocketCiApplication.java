package com.xianbei.pocket;

import com.xianbei.pocket.pojo.JenkinJob;
import com.xianbei.pocket.utils.ApplicationPropertiesBindingPostProcessor;
import com.xianbei.pocket.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PocketCiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocketCiApplication.class, args);
	}
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setPort(9002);
		factory.setSessionTimeout(10, TimeUnit.MINUTES);
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
		return factory;
	}
	@Bean
	public ApplicationPropertiesBindingPostProcessor applicationPropertiesBindingPostProcessor() {
		return new ApplicationPropertiesBindingPostProcessor();
	}
	@Autowired
	private JenkinJob jenkinJob;

	@PostConstruct
	public void init(){
		Config.jenkinJob=jenkinJob;
	}
}
