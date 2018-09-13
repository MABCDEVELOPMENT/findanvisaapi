package com.anvisa.config;

import java.util.Collections;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SuppressWarnings("deprecation")
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {
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
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println(System.getProperty("user.dir")+"/findimage/");
        registry
          .addResourceHandler("/findimage/**")
          .addResourceLocations(System.getProperty("user.dir")+"/findimage/").setCachePeriod(0);
    }
	
}
