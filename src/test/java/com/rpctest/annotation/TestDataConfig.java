package com.rpctest.annotation;

import java.lang.annotation.*;

/**
 * Created by duanzonghai on 2018/3/16.
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented

public @interface TestDataConfig {
    TestDataItem[] values();
}
