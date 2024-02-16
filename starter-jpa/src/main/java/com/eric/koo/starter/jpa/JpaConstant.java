package com.eric.koo.starter.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaConstant {

    public static final String SYSTEM = "SYSTEM";
    public static final String TEMP = "TEMP";

    private static final String PROPERTIES_PREFIX_BASE = "jpa.";
    public static final String PROPERTIES_PREFIX_AUDIT = PROPERTIES_PREFIX_BASE + "audit";

    public static final String ERROR_MULTIPLE_CODE_GENERATOR = "Field is annotated by multiple code generator";
}
