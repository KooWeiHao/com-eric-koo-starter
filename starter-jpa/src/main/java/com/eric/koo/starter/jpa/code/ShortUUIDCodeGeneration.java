package com.eric.koo.starter.jpa.code;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.tuple.ValueGenerator;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

public class ShortUUIDCodeGeneration extends AbstractCodeGeneration<ShortUUIDCode> {

    @Override
    public ValueGenerator<String> getValueGenerator() {
        return (session, o) -> prefix.concat(generateShortUUIDCode());
    }

    private String generateShortUUIDCode() {
        // Convert UUID to int
        var uuid = UUID.randomUUID();
        var uuidByteArray = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
        var uuidInt = ByteBuffer.wrap(uuidByteArray).getInt();

        new Random().nextInt(10);

        // Ensure that the ID is always having the size of 10
        return StringUtils.rightPad(
                String.valueOf(Math.abs(uuidInt)),
                10,
                String.valueOf(RandomUtils.nextInt(0, 10))
        );
    }
}
