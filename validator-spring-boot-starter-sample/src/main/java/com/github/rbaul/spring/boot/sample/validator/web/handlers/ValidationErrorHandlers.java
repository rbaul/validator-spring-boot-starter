package com.github.rbaul.spring.boot.sample.validator.web.handlers;

import com.github.rbaul.spring.boot.sample.validator.web.dtos.ErrorDto;
import com.github.rbaul.spring.boot.sample.validator.web.dtos.ValidationErrorDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationErrorHandlers {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleValidationError(MethodArgumentNotValidException ex) {
        return ErrorDto.builder()
                .errorCode("ERROR")
                .errors(Collections.unmodifiableSet(getValidationErrorDtos(ex.getBindingResult())))
                .message(ex.getLocalizedMessage())
                .build();
    }

    private Set<ValidationErrorDto> getValidationErrorDtos(Errors errors) {
        return Stream.concat(
                errors.getFieldErrors().stream()
                        .map(err -> ValidationErrorDto.builder()
                                .errorCode(err.getCode())
                                .fieldName(err.getField())
                                .rejectedValue(err.getRejectedValue())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()),
                errors.getGlobalErrors().stream()
                        .map(err -> ValidationErrorDto.builder()
                                .errorCode(err.getCode())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()))
                .collect(Collectors.toSet());
    }

    private String[] collectArguments(Object[] arguments) {
        List<String> listOfArguments = Objects.isNull(arguments) ?
                Collections.emptyList() :
                Stream.of(arguments)
                        .skip(1)
                        .map(Object::toString)
                        .collect(Collectors.toList());
        return listOfArguments.toArray(new String[0]);
    }

}