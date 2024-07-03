package com.metene.service.common;

import com.metene.service.dto.Error;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;

import java.util.Set;

public interface ResponseErrorBuilder {
    Error buildBadRequestError(HttpStatus status, Set<ConstraintViolation<Object>> violations);
    Error buildInternalServerError(HttpStatus status);
}
