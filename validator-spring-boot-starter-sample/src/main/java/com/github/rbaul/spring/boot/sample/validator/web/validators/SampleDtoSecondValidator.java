package com.github.rbaul.spring.boot.sample.validator.web.validators;

import com.github.rbaul.spring.boot.sample.validator.web.dtos.SampleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class SampleDtoSecondValidator implements Validator, ValidationGroups.Group3, ValidationGroups.Group2 {
    @Override
    public boolean supports(Class<?> clazz) {
        return SampleDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "validator.sampleDto.name.empty");
    }
}
