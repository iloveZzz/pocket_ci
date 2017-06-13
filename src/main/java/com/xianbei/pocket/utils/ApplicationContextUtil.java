package com.xianbei.pocket.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;//声明一个静态变量保存

	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
		context = applicationContext;
	}

	public static ApplicationContext getContext() {
		return context;
	}
	
}