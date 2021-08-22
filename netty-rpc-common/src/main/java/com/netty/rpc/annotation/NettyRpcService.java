package com.netty.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hdz
 * @description 获得被代理类的类型和版本信息
 * @create 2021年08月17日 13:57
 */
@Target({ElementType.TYPE})     //type表示可以加在类上
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface NettyRpcService {

    Class<?> value();
    String version() default "";
}
