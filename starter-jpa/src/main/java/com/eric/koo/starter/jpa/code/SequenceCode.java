package com.eric.koo.starter.jpa.code;

import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ValueGenerationType(generatedBy = SequenceCodeGeneration.class)
public @interface SequenceCode {
    String prefix();

    int padding() default 8;

    boolean isRequiredDate() default true;
}
