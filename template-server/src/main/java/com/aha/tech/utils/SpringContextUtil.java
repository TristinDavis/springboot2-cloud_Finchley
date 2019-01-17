package com.aha.tech.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 获取spring容器，以访问容器中定义的其他bean
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static Logger logger = LogManager.getLogger(SpringContextUtil.class);

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;
	// 测试用,将某个bean替换掉
	private static Map<String, Object> mockMap = new HashMap<>();

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 *
	 * @param applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.info("=======setApplicationContext=======");
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 *
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		Object o = mockMap.get(name);
		if (o != null) {
			return (T) o;
		}
		if (applicationContext == null) {
			logger.info(name + "==null");
			return null;
		} else {
			return (T) applicationContext.getBean(name);
		}
	}

	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return (T) applicationContext.getBean(clazz);
	}

	public static void addBean(String name, Object obj) {
		mockMap.put(name, obj);
	}
}
