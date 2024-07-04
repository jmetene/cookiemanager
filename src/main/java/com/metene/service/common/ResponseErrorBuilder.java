package com.metene.service.common;

import com.metene.service.dto.ResponseHttpError;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;

import java.util.Set;

public interface ResponseErrorBuilder {
    ResponseHttpError buildBadRequestError(HttpStatus status, Set<ConstraintViolation<Object>> violations);
    ResponseHttpError buildBadRequestError(HttpStatus status, String message);
    ResponseHttpError buildInternalServerError(HttpStatus status);
}
