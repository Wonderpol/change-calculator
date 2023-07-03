package com.piaskowy.commandLineArgument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Option {
    String name();

    String defaultValue() default "";

    boolean hasValue() default false;

    double minValue() default Double.MIN_VALUE;

    double maxValue() default Double.MAX_VALUE;

    String[] validValues() default {};

    boolean required() default false;
}
