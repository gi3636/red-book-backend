package com.example.red.book.interceptor;

import java.lang.annotation.*;


@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WhiteList {
}
