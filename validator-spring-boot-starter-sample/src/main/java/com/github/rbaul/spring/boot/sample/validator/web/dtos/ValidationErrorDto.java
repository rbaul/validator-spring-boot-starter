package com.github.rbaul.spring.boot.sample.validator.web.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@ApiModel("ValidationError")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"fieldName", "errorCode"})
public class ValidationErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(readOnly = true)
    private String fieldName;

    @ApiModelProperty(readOnly = true)
    private String errorCode;

    @ApiModelProperty(readOnly = true)
    private String message;

    @ApiModelProperty(readOnly = true)
    private Object rejectedValue;

    @ApiModelProperty(readOnly = true)
    private Object[] params;

}