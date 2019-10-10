package com.roozbeh.webcrawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author rezaeian
 */
@Configuration
@EnableSwagger2
@EnableAsync
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/error).+"))
                .paths(PathSelectors.regex("(?!/actuator).+"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Crawler Microservice API",
                "This micro service is designed for saving and fetching and crawling products from specific website (http://magento-test.finology.com.my/)" ,
                "1.1.1",
                "No terms for service",
                new Contact("Roozbeh Rezaeian", "https://ir.linkedin.com/in/r1oozbeh", "roozbe@mail.com"),
                "Personal Project", "Self license", Collections.emptyList());
    }
}
