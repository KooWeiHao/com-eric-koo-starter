package com.eric.koo.starter.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaConstant {

    public static final String SYSTEM = "SYSTEM";

    private static final String PROPERTIES_PREFIX_BASE = "jpa.";
    public static final String PROPERTIES_PREFIX_AUDIT = PROPERTIES_PREFIX_BASE + "audit";
}
