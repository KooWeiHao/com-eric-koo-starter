package com.eric.koo.starter.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaConstant {

    private static final String PROPERTIES_PREFIX_BASE = "jpa.";
    public static final String PROPERTIES_PREFIX_AUDIT = PROPERTIES_PREFIX_BASE + "audit";

    public static final String ATTRIBUTE_PREFIX = "prefix";
}
