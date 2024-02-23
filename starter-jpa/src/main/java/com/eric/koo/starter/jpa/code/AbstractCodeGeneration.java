package com.eric.koo.starter.jpa.code;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;

import java.lang.annotation.Annotation;

@Getter
@Setter
abstract class AbstractCodeGeneration<A extends Annotation> implements AnnotationValueGeneration<A> {

    private String prefix;

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
