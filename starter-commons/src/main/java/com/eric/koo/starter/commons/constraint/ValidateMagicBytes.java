package com.eric.koo.starter.commons.constraint;

import com.eric.koo.starter.commons.constraint.validator.MagicBytesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MagicBytesValidator.class)
public @interface ValidateMagicBytes {
    String message() default "Invalid magic bytes - [{FILENAME}]";

    String bytesField();

    String filenameField();

    String mimeTypeField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
