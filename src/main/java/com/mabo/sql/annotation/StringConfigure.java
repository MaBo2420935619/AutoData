package com.mabo.sql.annotation;
/**
 * @Description : 在当前修饰的方法前后执行其他的方法
 * @Author : mabo
*/

import com.mabo.sql.StringRule;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringConfigure {
    //在数据库中的该属性的列名
    String fieldName() default "";
    //生成测试数据的规则,NAME,TELNUMBER,UUID16,UUID,METHOD
    StringRule createRule() default StringRule.UUID16;
    //当createRule 为METHOD以下两个参数才生效
    //用来指明自定义的参数生成规则
    Class aClass() default Class.class;
    String method() default "";
}
