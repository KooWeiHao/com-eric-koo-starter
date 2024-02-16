package com.eric.koo.starter.jpa.code;

import com.eric.koo.starter.jpa.JpaConstant;
import com.eric.koo.starter.jpa.code.annotation.ShortUUIDCode;
import com.eric.koo.starter.jpa.code.annotation.UUIDCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ReflectionUtils;

import javax.persistence.PrePersist;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CodeGenerationListener {

    @Value("#{new java.util.Random()}")
    private Random random;

    private static final List<Class<? extends Annotation>> codeAnnotationClasses = List.of(UUIDCode.class, ShortUUIDCode.class);

    @PrePersist
    private void generateCode(Object entity) {
        ReflectionUtils.doWithFields(
                entity.getClass(),
                field -> {
                    var code = new AtomicReference<>();

                    Optional.ofNullable(field.getAnnotation(UUIDCode.class))
                            .ifPresent(uuidCode -> {
                                var generatedCode = uuidCode.prefix().concat(generateUUIDCode());
                                code.set(generatedCode);
                            });

                    Optional.ofNullable(field.getAnnotation(ShortUUIDCode.class))
                            .ifPresent(shortUUIDCode -> {
                                var generatedCode = shortUUIDCode.prefix().concat(generateShortUUIDCode());
                                code.set(generatedCode);
                            });

                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, entity, code.get());
                },
                this::filterCodeField
        );
    }

    private boolean filterCodeField(Field field) {
        var annotations = Arrays.stream(field.getAnnotations())
                .filter(annotation -> codeAnnotationClasses.contains(annotation.annotationType()))
                .collect(Collectors.toList());

        switch (annotations.size()) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new CodeGenerationException(JpaConstant.ERROR_MULTIPLE_CODE_GENERATOR);
        }
    }

    private String generateUUIDCode() {
       return UUID.randomUUID().toString()
                .replace("-", "");
    }

    private String generateShortUUIDCode() {
        // Convert UUID to int
        var uuid = UUID.randomUUID();
        var uuidByteArray = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
        var uuidInt = ByteBuffer.wrap(uuidByteArray).getInt();

        // Ensure that the ID is always having the size of 10
        return StringUtils.rightPad(
                String.valueOf(Math.abs(uuidInt)),
                10,
                String.valueOf(random.nextInt(10))
        );
    }
}
