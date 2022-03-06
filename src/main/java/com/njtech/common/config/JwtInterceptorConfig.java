package com.njtech.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedHeaders("Content-Type", "X-Requested-With", "accept,Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "token")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

    @Bean
    public HandlerInterceptor getJwtAuthenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * Account user = (Account) request.getSession().getAttribute("user");
         *         System.out.println("执行了JwtInterceptor");
         *         if (user == null) {
         *             System.out.println("执行了JwtInterceptor2");
         *             // 重定向到登录页
         *             response.sendRedirect("https://baidu.com/");
         *             return false;
         *         }
         *         return true;
         */

        // 实现WebMvcConfigurer不会导致静态资源被拦截
        registry.addInterceptor(getJwtAuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.html", "/register.html",
                        "/swagger-ui.html/**", "/swagger-resources/**");
    }
}

