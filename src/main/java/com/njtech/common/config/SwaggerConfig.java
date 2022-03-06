package com.njtech.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 有token
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("token").description("令牌（登录和注册接口不填）")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
                .build().globalOperationParameters(parameters);
    }

    /**
     * 无token
     *
     * @Bean public Docket createRestApi() {
     * return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
     * .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
     * .build();
     * }
     */

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("COLLECT后端开发接口文档")
                .description("**请忽略basic-error-controller**\n测试github部署")
                .version("7.5")
                .build();
    }
}