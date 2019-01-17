package com.aha.tech.config;

import com.aha.tech.base.commons.response.RpcResponse;
import com.fasterxml.classmate.types.ResolvedObjectType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;


@Configuration
@ConditionalOnProperty(name = "swagger.enable", havingValue = "on")
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket hjmServerApi() {
        ResolvedObjectType resolvedType = ResolvedObjectType.create(RpcResponse.class, null, null, null);
        return new Docket(DocumentationType.SWAGGER_2).additionalModels(resolvedType).groupName("server-api")
                .apiInfo(apiInfo())
                .select().apis(or(RequestHandlerSelectors.basePackage("com.aha.tech.controller")))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("[支付系统]API文档").description("JAVA 服务")
                .version("2.0")// 版本显示
                .build();
    }
}
