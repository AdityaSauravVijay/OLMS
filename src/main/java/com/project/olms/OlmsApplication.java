package com.project.olms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"com.project.olms.controller", "com.project.olms.pojo", "com.project.olms.validator", "com.project.olms.dao"})
public class OlmsApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(OlmsApplication.class, args);
	}
	
	@Override

	 public void addViewControllers(ViewControllerRegistry registry) {

	  registry.addViewController("/").setViewName("splash-page");

	 }

}
