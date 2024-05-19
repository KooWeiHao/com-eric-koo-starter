package com.eric.koo.starter.commons.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaskingUtil {

    public static final String DEFAULT_MASK_CHARACTER = "*";
    public static final int DEFAULT_REPEATED_MASK_CHARACTER = 8;

    public static String mask(String value) {
        if(Optional.ofNullable(value).isPresent()) {
            return StringUtils.repeat(DEFAULT_MASK_CHARACTER, DEFAULT_REPEATED_MASK_CHARACTER);
        }

        return null;
    }
}
