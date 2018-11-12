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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import com.anvisa.interceptor.ScheduledTasks;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.SynchronizeDataMdbTask;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableWebMvc
@EntityScan(basePackages = {"com.anvisa.model.persistence"})
@EnableJpaRepositories("com.anvisa.repository")
public class FindAnvisaApplication extends SpringBootServletInitializer implements WebMvcConfigurer {
	
	
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
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		
	        registry.addResourceHandler("/findimage/**")
	                .addResourceLocations("file:///./home/findinfo/findimage/")
	                .resourceChain(true)
	                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
	        
	    
	} 
	    

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		
		File dir = new File(
				System.getProperty("file.separator") + "home" + System.getProperty("file.separator") + "findinfo"
						+ System.getProperty("file.separator") + "findimage" + System.getProperty("file.separator"));
		if (!dir.exists()) {
			dir.mkdir();
		}
		IMAGE_DIR = dir.getCanonicalPath();

		SpringApplication.run(FindAnvisaApplication.class, args);
		
		System.out.println(IMAGE_DIR);
		//SynchronizeDataTask.synchronizeData();
		SynchronizeDataMdbTask synchronizeDataMdbTask = new SynchronizeDataMdbTask (false,
		false,
		false,
		false,
		false,
		true,
		true);
		synchronizeDataMdbTask.synchronizeData();
		
	}
}
