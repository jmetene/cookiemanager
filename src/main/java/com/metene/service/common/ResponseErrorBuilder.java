package com.metene.service.common;

import com.metene.service.dto.HttpErrorResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;

import java.util.Set;

public interface ResponseErrorBuilder {
    HttpErrorResponse buildBadRequestError(HttpStatus status, Set<ConstraintViolation<Object>> violations);
    HttpErrorResponse buildBadRequestError(HttpStatus status, String message);
    HttpErrorResponse buildInternalServerError(HttpStatus status);
}
