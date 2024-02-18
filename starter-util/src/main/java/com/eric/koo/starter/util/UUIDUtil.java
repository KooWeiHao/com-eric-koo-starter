package com.eric.koo.starter.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UUIDUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString()
                .replace("-", StringUtils.EMPTY);
    }

    public static String generateShortUUID() {
        // Convert UUID to int
        var uuid = UUID.randomUUID();
        var uuidByteArray = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
        var uuidInt = ByteBuffer.wrap(uuidByteArray).getInt();

        // Ensure that the ID is always having the size of 10
        var secureRandom = new SecureRandom();
        return StringUtils.rightPad(
                String.valueOf(Math.abs(uuidInt)),
                10,
                String.valueOf(secureRandom.nextInt(10))
        );
    }
}
