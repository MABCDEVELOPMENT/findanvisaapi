package com.anvisa;

import java.util.Collections;

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
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
//@EnableScheduling
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.anvisa.model.persistence" })
@EnableJpaRepositories("com.anvisa.repository")
public class FindAnvisaApplication extends SpringBootServletInitializer {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_12).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).paths(Predicates.not(PathSelectors.regex("/error"))).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Api Rest NEConsult", "", "", "Terms of service",
				new Contact("NEConsult", "www.neconsult.com.br", "suport@neconsult.com.br"), "License of API",
				"API license URL", Collections.emptyList());
	}
	

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
	}
}
