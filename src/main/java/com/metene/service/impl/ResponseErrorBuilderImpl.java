package com.metene.service.impl;

import com.metene.service.common.ResponseErrorBuilder;
import com.metene.service.dto.Error;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ResponseErrorBuilderImpl implements ResponseErrorBuilder {

    @Override
    public Error buildBadRequestError(HttpStatus status, Set<ConstraintViolation<Object>> violations) {
        return  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().toString())
                .extendedInfo(violations.stream().map(ConstraintViolation::getMessage).toList())
                .build();
    }

    @Override
    public Error buildInternalServerError(HttpStatus status) {
        return  Error
                .builder()
                .code(String.valueOf(status.value()))
                .description(status.getReasonPhrase())
                .dateTime(LocalDateTime.now().toString())
                .build();
    }
}
