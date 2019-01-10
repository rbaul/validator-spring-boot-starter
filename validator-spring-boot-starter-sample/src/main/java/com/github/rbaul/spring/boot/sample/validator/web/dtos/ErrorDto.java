package com.github.rbaul.spring.boot.sample.validator.web.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@ApiModel("Error")
@Data
@Builder
@EqualsAndHashCode(of = {"errorCode", "timestamp"})
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(readOnly = true)
    private Object errorCode;

    @ApiModelProperty(readOnly = true)
    @Builder.Default private Calendar timestamp = Calendar.getInstance();

    @ApiModelProperty(allowEmptyValue = true)
    @Singular
    private Set<Object> errors;

    @ApiModelProperty(allowEmptyValue = true)
    private String message;
}