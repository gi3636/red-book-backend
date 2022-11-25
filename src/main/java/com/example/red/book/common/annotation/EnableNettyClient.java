package com.example.red.book.common.annotation;

import com.example.red.book.config.ClientBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ClientBoot.class)
public @interface EnableNettyClient {
}
