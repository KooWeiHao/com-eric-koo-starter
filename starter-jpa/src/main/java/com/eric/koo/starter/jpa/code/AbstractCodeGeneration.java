package com.eric.koo.starter.jpa.code;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

abstract class AbstractCodeGeneration<A extends Annotation> implements AnnotationValueGeneration<A> {

    public String prefix;

    @Override
    public void initialize(A a, Class<?> aClass) {
        this.prefix = Optional.ofNullable(AnnotationUtils.getValue(a, "prefix"))
                .map(Object::toString)
                .orElse(StringUtils.EMPTY);
    }

    @Override
    public GenerationTiming getGenerationTiming() {
        return GenerationTiming.INSERT;
    }

    @Override
    public boolean referenceColumnInSql() {
        return false;
    }

    @Override
    public String getDatabaseGeneratedReferencedColumnValue() {
        return null;
    }
}
