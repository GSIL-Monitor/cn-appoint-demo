package com.jd.appoint.store.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by bjliuyong on 2018/5/17.
 * jvm 参数加-Dspring.profiles.active=dev 启用swagger
 */
@EnableWebMvc
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.jd.appoint.store.web"})
@Profile("dev")
public class SwagConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.jd.appoint.store.web"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("REST API")
            .description("门店网关接口")
            .termsOfServiceUrl("http://appoint.mendian.jd.com/")
            .contact("lishuaiwei")
            .version("1.0")
            .build();
    }
}
