package com.rpctest.listener;

import com.pentos.kaitest.util.AnnotationUtil;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by duanzonghai on 2018/3/15.
 */
public class AnnotationMethodUtil extends AnnotationUtil {

    public static Annotation findAnnotation(Method method, Class<? extends Annotation> annotationType){

        Annotation annotation = AnnotationUtils.findAnnotation(method,annotationType);

        //如果annotation 为空,则返回AnnotationUtils.findAnnotation(),否则返回annotation的值
        return  annotation == null ? AnnotationUtils.findAnnotation(method.getDeclaringClass(),annotationType)
                :annotation;
    }

}
