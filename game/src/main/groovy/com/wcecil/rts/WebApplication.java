package com.wcecil.rts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wcecil.rts.configuration.SchedulerConfig;

@SpringBootApplication
@EnableMongoRepositories
@Import({SchedulerConfig.class})
public class WebApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/images/**").addResourceLocations(
				"/images/", "classpath:/images/");
		
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/web/", "classpath:/web/");
	}
}
