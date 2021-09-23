package com.prueba.mercado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.prueba.mercado"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Prueba técnica Mercado Libre",
                "Prueba de ingreso Mercado Libre",
                "1.0",
                "https://www.linkedin.com/in/sebastian-agudelo-ruiz",
                new Contact("Sebastian", "https://www.linkedin.com/in/sebastian-agudelo-ruiz", "sebasar751@gmail.com"),
                "LICENSE",
                "https://www.linkedin.com/in/sebastian-agudelo-ruiz",
                Collections.emptyList()
        );
    }
}
