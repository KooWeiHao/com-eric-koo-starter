package com.eric.koo.starter.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebConstant {

    private static final String PROPERTIES_PREFIX_BASE = "web.";
    public static final String PROPERTIES_PREFIX_CORS = PROPERTIES_PREFIX_BASE + "cors";
    public static final String PROPERTIES_PREFIX_STANDARD_EXCEPTION_HANDLER = PROPERTIES_PREFIX_BASE + "standard.exception.handler";
    public static final String PROPERTIES_PREFIX_SWAGGER = PROPERTIES_PREFIX_BASE + "swagger";

    public static final String PATH_PATTERN_ANY = "/**";

    public static final String PREFIX_REQUEST_REFERENCE_ID = "REQ";

    public static final String ERROR_UNAVAILABLE_REQUEST_PATH = "Request path is unavailable";
}
