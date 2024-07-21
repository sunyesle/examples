package com.sunyesle.spring_boot_excel.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String headerName() default "";

    StyleType headerStyle() default StyleType.HEADER_STYLE;

    StyleType dataStyle() default StyleType.DATA_STYLE;

    int width() default 4000;
}
