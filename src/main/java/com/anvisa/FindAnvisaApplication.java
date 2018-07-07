package com.anvisa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.anvisa.interceptor.ScheduledTasks;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = { "com.anvisa.model.persistence" })
@EnableJpaRepositories("com.anvisa.repository")
public class FindAnvisaApplication extends SpringBootServletInitializer {

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
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FindAnvisaApplication.class);
    }

	/*public static void main(String[] args) {
		SpringApplication.run(FindAnvisaApplication.class, args);
	}*/
}
