package com.example.red.book.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public WebConfig() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/v3/api-docs")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/swagger-ui/")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/swagger-resources/**")
                // .excludePathPatterns("/swagger-ui/**")
                //.excludePathPatterns("/user/**")
                .excludePathPatterns("/sts/**")
                //.excludePathPatterns("/video/**")
                .excludePathPatterns("/api/auth/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/show/**")
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/fontawesome-free-5.11.2-web/**", "/favicon.ico");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //?????????/static/** ??????????????????classpath:/static/ ?????????
        //addResourceLocations????????????????????????'/'??????,?????????????????????,????????????????????????????????????????????????(??????: classpath:/xxx/xx/, file:/xxx/xx/, http://xxx/xx/)
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
//        registry.addResourceHandler("/img/**").addResourceLocations("file:/"+path);
        //?????????????????????????????????
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

    }
}
