package com.qima.o2o.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//等价于<mvc:annotation-driven/>
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean(name="viewResolver")
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setApplicationContext(this.applicationContext);
		viewResolver.setCache(false);
		viewResolver.setPrefix("/WEB-INF/html/");
		viewResolver.setSuffix(".html");
		
		return viewResolver;
	}
	
	public CommonsMultipartResolver createMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}

}
