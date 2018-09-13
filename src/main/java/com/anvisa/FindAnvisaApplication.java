package com.anvisa;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.anvisa.interceptor.ScheduledTasks;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SpringBootApplication
//@EnableScheduling
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.anvisa.model.persistence" })
@EnableJpaRepositories("com.anvisa.repository")
public class FindAnvisaApplication extends SpringBootServletInitializer {
	
	
	public static String IMAGE_DIR;	

	@Bean
	public ScheduledTasks scheduledTasks() {
		return new ScheduledTasks();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return new Jackson2ObjectMapperBuilderCustomizer() {
			@SuppressWarnings("deprecation")
			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
				// TODO Auto-generated method stub
				jacksonObjectMapperBuilder.dateFormat(new ISO8601DateFormat());
			}
		};
	}
	
	 
	    

	public static void main(String[] args) {
		

		SpringApplication.run(FindAnvisaApplication.class, args);
		try {
			IMAGE_DIR = new File(".").getCanonicalPath() +System.getProperty("file.separator")+ "findimage"+System.getProperty("file.separator");
			System.out.println(IMAGE_DIR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
