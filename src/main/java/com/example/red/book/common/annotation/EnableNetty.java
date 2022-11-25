package com.example.red.book.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableNettyClient
@EnableNettyServer
public @interface EnableNetty {
}

