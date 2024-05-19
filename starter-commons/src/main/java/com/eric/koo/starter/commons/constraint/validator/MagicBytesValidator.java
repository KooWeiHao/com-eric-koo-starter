package com.eric.koo.starter.commons.constraint.validator;

import com.eric.koo.starter.commons.constraint.ValidateMagicBytes;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.support.DefaultConversionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class MagicBytesValidator implements ConstraintValidator<ValidateMagicBytes, Object> {

    private String bytesField;
    private String filenameField;
    private String mimeTypeField;

    @Override
    public void initialize(ValidateMagicBytes constraintAnnotation) {
        this.bytesField = constraintAnnotation.bytesField();
        this.filenameField = constraintAnnotation.filenameField();
        this.mimeTypeField = constraintAnnotation.mimeTypeField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        // Prepare data
        var conversionService = new DefaultConversionService();
        var documentWrapper = PropertyAccessorFactory.forBeanPropertyAccess(o);
        var bytes = conversionService.convert(documentWrapper.getPropertyValue(bytesField), byte[].class);
        var filename = conversionService.convert(documentWrapper.getPropertyValue(filenameField), String.class);
        var mimeType = conversionService.convert(documentWrapper.getPropertyValue(mimeTypeField), String.class);

        // Skip validation
        if(ArrayUtils.isEmpty(bytes) || StringUtils.isAnyBlank(filename, mimeType)) {
            log.debug("Magic bytes validation is skipped - bytesLength: [{}], filename: [{}], mimeType: [{}]", ArrayUtils.getLength(bytes), filename, mimeType);
            return true;
        }

        // Prepare error message
        constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)
                .addMessageParameter("FILENAME", filename);

        // Perform Validation
        var tika = new Tika();
        var detectedMimeType = tika.detect(bytes, filename);
        log.debug("filename: [{}], expectedMimeType: [{}], actualMimeType: [{}]", filename, mimeType, detectedMimeType);

        return detectedMimeType.equals(mimeType);
    }
}
