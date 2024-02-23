package com.eric.koo.starter.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaConstant {

    private static final String PROPERTIES_PREFIX_BASE = "jpa.";
    public static final String PROPERTIES_PREFIX_AUDIT = PROPERTIES_PREFIX_BASE + "audit";

    public static final String ATTRIBUTE_PADDING = "padding";
    public static final String ATTRIBUTE_IS_REQUIRED_DATE = "isRequiredDate";
    public static final String ATTRIBUTE_PREFIX = "prefix";
    public static final String ATTRIBUTE_FOR_DATE = "forDate";
    public static final String ATTRIBUTE_SEQUENCE = "sequence";
    public static final String ATTRIBUTE_IS_SHORT = "isShort";

    public static final String SQL_LOCK_AND_SELECT_LATEST_SEQUENCE_FROM_SMART_SEQUENCE = String.join(StringUtils.SPACE,
            "SELECT `sequence`",
            "FROM `smart_sequence`",
            "WHERE `prefix` = :prefix",
            "AND (`for_date` = :forDate OR `for_date` IS NULL)",
            "ORDER BY `sequence` DESC",
            "LIMIT 1",
            "FOR UPDATE"
    );
    public static final String SQL_INSERT_INTO_SMART_SEQUENCE = String.join(StringUtils.SPACE,
            "INSERT INTO `smart_sequence`",
            "(`prefix`, `for_date`, `sequence`)",
            "VALUES (:prefix, :forDate, :sequence)"
    );
}
