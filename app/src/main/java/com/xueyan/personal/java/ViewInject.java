package com.xueyan.personal.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface ViewInject {
        int value();
}
