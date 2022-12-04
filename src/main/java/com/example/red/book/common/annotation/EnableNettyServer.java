package com.example.red.book.common.annotation;

import com.example.red.book.netty.ServerBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Import(ServerBoot.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableNettyServer {
}

