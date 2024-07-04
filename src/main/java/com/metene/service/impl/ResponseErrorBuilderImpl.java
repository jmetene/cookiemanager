package com.metene.service.impl;

import com.metene.service.common.ResponseErrorBuilder;
import com.metene.service.dto.Error;
import com.metene.service.dto.ResponseHttpError;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

@Service
public class ResponseErrorBuilderImpl implements ResponseErrorBuilder {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public ResponseHttpError buildBadRequestError(HttpStatus status, Set<ConstraintViolation<Object>> violations) {
        Error error =  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().format(getFormatter()))
                .violations(violations.stream().map(ConstraintViolation::getMessage).toList())
                .build();
        return ResponseHttpError.builder().error(error).build();
    }

    @Override
    public ResponseHttpError buildBadRequestError(HttpStatus status, String message) {
        Error error =  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().format(getFormatter()))
                .violations(Collections.singletonList(message))
                .build();

        return ResponseHttpError.builder().error(error).build();
    }

    @Override
    public ResponseHttpError buildInternalServerError(HttpStatus status) {
        Error error =  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().format(getFormatter()))
                .build();

        return ResponseHttpError.builder().error(error).build();
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(PATTERN, new Locale("es"));
    }
}
