package com.eric.koo.starter.jpa.code;

import com.eric.koo.starter.jpa.JpaConstant;
import com.eric.koo.starter.util.UUIDUtil;
import org.hibernate.tuple.ValueGenerator;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Optional;

public class UUIDCodeGeneration extends AbstractCodeGeneration<UUIDCode> {

    private boolean isShort;

    @Override
    public void initialize(UUIDCode uuidCode, Class<?> aClass) {
        var prefix = Optional.ofNullable(AnnotationUtils.getValue(uuidCode, JpaConstant.ATTRIBUTE_PREFIX))
                .map(p -> p.toString().trim())
                .orElseThrow(() -> new CodeGenerationException("prefix is null"));
        setPrefix(prefix);

        this.isShort = Optional.ofNullable(AnnotationUtils.getValue(uuidCode, JpaConstant.ATTRIBUTE_IS_SHORT))
                .map(is -> Boolean.parseBoolean(is.toString()))
                .orElseThrow(() -> new CodeGenerationException("isShort is null"));
    }

    @Override
    public ValueGenerator<String> getValueGenerator() {
        return (session, o) -> {
            var uuid = isShort
                    ? UUIDUtil.generateShortUUID()
                    : UUIDUtil.generateUUID();
            return getPrefix().concat(uuid);
        };
    }
}
