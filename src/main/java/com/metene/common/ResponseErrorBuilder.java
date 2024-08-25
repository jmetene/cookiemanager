package com.metene.common;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

@Component
public class ResponseErrorBuilder {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public HttpErrorResponse buildBadRequestError(HttpStatus status, Set<ConstraintViolation<Object>> violations) {
        Error error =  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().format(getFormatter()))
                .violations(violations.stream().map(ConstraintViolation::getMessage).toList())
                .build();
        return HttpErrorResponse.builder().error(error).build();
    }

    public HttpErrorResponse buildBadRequestError(HttpStatus status, String message) {
        Error error =  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().format(getFormatter()))
                .violations(Collections.singletonList(message))
                .build();

        return HttpErrorResponse.builder().error(error).build();
    }

    public HttpErrorResponse buildInternalServerError(HttpStatus status) {
        Error error =  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().format(getFormatter()))
                .build();

        return HttpErrorResponse.builder().error(error).build();
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(PATTERN, new Locale("es"));
    }
}
