package com.eric.koo.starter.jpa.code;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.tuple.ValueGenerator;

import java.util.UUID;

public class UUIDCodeGeneration extends AbstractCodeGeneration<UUIDCode> {

    @Override
    public ValueGenerator<String> getValueGenerator() {
        return (session, o) -> prefix.concat(generateUUIDCode());
    }

    private String generateUUIDCode() {
        return UUID.randomUUID().toString()
                .replace("-", StringUtils.EMPTY);
    }
}
