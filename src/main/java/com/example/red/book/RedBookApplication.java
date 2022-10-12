package com.example.red.book;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableCaching
@Import(BeanValidatorPluginsConfiguration.class)
@SpringBootApplication()
public class RedBookApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedBookApplication.class, args);
        System.out.println(111);

    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
