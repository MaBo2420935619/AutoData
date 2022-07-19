package com.mabo.sql.annotation;
/**
 * @Description : 在当前修饰的方法前后执行其他的方法
 * @Author : mabo
*/
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IntConfigure {
    String fieldName() default "";

    int max() default 100;

    int min() default 0;

}
