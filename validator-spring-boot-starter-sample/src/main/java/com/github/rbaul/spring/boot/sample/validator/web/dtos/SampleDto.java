package com.github.rbaul.spring.boot.sample.validator.web.dtos;

import com.github.rbaul.spring.boot.sample.validator.web.validators.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldNameConstants
public class SampleDto {
    @Min(value = 1, groups = ValidationGroups.Group1.class)
    @Max(2)
    private Long id;

    @NotNull
    private String name;

    private String description;

}
