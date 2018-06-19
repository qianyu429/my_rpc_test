package com.rpctest.annotation;

import java.lang.annotation.*;

/**
 * Created by duanzonghai on 2018/3/16.
 */
@Target({ElementType.METHOD,ElementType.TYPE}) //作用于方法,类,接口或声明
@Retention(RetentionPolicy.RUNTIME)  //保存到运行时
@Documented //被修饰的注解会被生成到javadoc中
@Inherited  //可以让注解被继承，但这并不是真的继承，只是通过使用@Inherited，可以让子类Class对象使用getAnnotations()获取父类被@Inherited修饰的注解

public @interface TestDataRule {
    String id() default "";
    String purpose() default "";
}
