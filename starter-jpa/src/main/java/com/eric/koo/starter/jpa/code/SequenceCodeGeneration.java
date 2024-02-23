package com.eric.koo.starter.jpa.code;

import com.eric.koo.starter.jpa.JpaConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;
import org.hibernate.type.IntegerType;
import org.springframework.core.annotation.AnnotationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SequenceCodeGeneration extends AbstractCodeGeneration<SequenceCode> {

    private int padding;
    private boolean isRequiredDate;

    @Override
    public void initialize(SequenceCode sequenceCode, Class<?> aClass) {
        var prefix = Optional.ofNullable(AnnotationUtils.getValue(sequenceCode, JpaConstant.ATTRIBUTE_PREFIX))
                .map(Object::toString)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new CodeGenerationException("prefix is null or empty"));
        setPrefix(prefix);

        this.padding = Optional.ofNullable(AnnotationUtils.getValue(sequenceCode, JpaConstant.ATTRIBUTE_PADDING))
                .map(p -> Integer.parseInt(p.toString()))
                .orElseThrow(() -> new CodeGenerationException("padding is null"));

        this.isRequiredDate = Optional.ofNullable(AnnotationUtils.getValue(sequenceCode, JpaConstant.ATTRIBUTE_IS_REQUIRED_DATE))
                .map(ird -> Boolean.parseBoolean(ird.toString()))
                .orElseThrow(() -> new CodeGenerationException("isRequiredDate is null"));
    }

    @Override
    public ValueGenerator<String> getValueGenerator() {
        return (session, o) -> {
            var s = session.getSessionFactory().openSession();

            try (s) {
                var transaction = s.beginTransaction();

                var now = this.isRequiredDate ? LocalDate.now() : null;
                var latestSequence = getLatestSequence(s, now);
                var newSequence = latestSequence.orElse(0) + 1;
                createSmartSequence(s, now, newSequence);

                transaction.commit();

                return createCode(newSequence, now);
            } catch (Exception exception) {
                if(s.getTransaction().isActive()) {
                    s.getTransaction().rollback();
                }

                throw exception;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private Optional<Integer> getLatestSequence(Session session, LocalDate date) {
        var query = session.createNativeQuery(JpaConstant.SQL_LOCK_AND_SELECT_LATEST_SEQUENCE_FROM_SMART_SEQUENCE);
        query.setParameter(JpaConstant.ATTRIBUTE_PREFIX, getPrefix());
        query.setParameter(JpaConstant.ATTRIBUTE_FOR_DATE, date);
        query.addScalar(JpaConstant.ATTRIBUTE_SEQUENCE, IntegerType.INSTANCE);

        return query.uniqueResultOptional();
    }

    private void createSmartSequence(Session session, LocalDate date, int sequence) {
        var query = session.createNativeQuery(JpaConstant.SQL_INSERT_INTO_SMART_SEQUENCE);
        query.setParameter(JpaConstant.ATTRIBUTE_PREFIX, getPrefix());
        query.setParameter(JpaConstant.ATTRIBUTE_FOR_DATE, date);
        query.setParameter(JpaConstant.ATTRIBUTE_SEQUENCE, sequence);
        query.executeUpdate();
    }

    private String createCode(int sequence, LocalDate date) {
        var code = new StringBuilder(getPrefix());

        Optional.ofNullable(date).ifPresent(d -> {
            var formattedDate = d.format(DateTimeFormatter.BASIC_ISO_DATE);
            code.append(formattedDate);
        });

        var formattedSequence = StringUtils.leftPad(String.valueOf(sequence), this.padding, '0');
        code.append(formattedSequence);

        return code.toString();
    }
}
