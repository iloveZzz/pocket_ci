package com.xianbei.pocket.utils;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface ApplicationProperties {

    String[] locations();

    String prefix() default "";

}