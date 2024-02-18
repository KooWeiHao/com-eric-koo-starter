package com.eric.koo.starter.jpa.code;

import com.eric.koo.starter.util.UUIDUtil;
import org.hibernate.tuple.ValueGenerator;

public class ShortUUIDCodeGeneration extends AbstractCodeGeneration<ShortUUIDCode> {

    @Override
    public ValueGenerator<String> getValueGenerator() {
        return (session, o) -> getPrefix().concat(UUIDUtil.generateShortUUID());
    }
}
