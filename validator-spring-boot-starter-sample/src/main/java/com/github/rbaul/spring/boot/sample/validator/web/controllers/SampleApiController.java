package com.github.rbaul.spring.boot.sample.validator.web.controllers;

import com.github.rbaul.spring.boot.sample.validator.web.dtos.SampleDto;
import com.github.rbaul.spring.boot.sample.validator.web.validators.SampleDtoFirstValidator;
import com.github.rbaul.spring.boot.sample.validator.web.validators.SampleDtoSecondValidator;
import com.github.rbaul.spring.boot.sample.validator.web.validators.ValidationGroups;
import com.github.rbaul.spring.boot.validator.SpringValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("sample")
@RestController
public class SampleApiController {

    @PostMapping
    public void createSampleNoValidate(@RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with all supported validators (except constraints)
     */
    @PostMapping("validator-with-all-validators")
    public void createSampleWithSpringValidatorAll(@SpringValidator @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with all supported validators (include constraints before)
     */
    @PostMapping("validator-with-all-validators-contstraint")
    public void createSampleWithConstraintSpringValidatorAll(@SpringValidator(validateConstraintBefore = true) @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with specific validators
     */
    @PostMapping("validator")
    public void createSampleWithSpringValidator(@SpringValidator({SampleDtoFirstValidator.class, SampleDtoSecondValidator.class}) @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with specific groups
     */
    @PostMapping("validator-group")
    public void createSampleWithSpringValidatorGroups(@SpringValidator(groups = {ValidationGroups.Group1.class, ValidationGroups.Group2.class}) @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with specific validators and groups
     */
    @PostMapping("validator-group-mix")
    public void createSampleWithSpringValidatorGroupsMix(@SpringValidator(validators = {SampleDtoFirstValidator.class}, groups = {ValidationGroups.Group1.class, ValidationGroups.Group2.class}) @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Mix @SpringValidator & @Validated
     * If @Validated will be find errors not will be execute @SpringValidator
     */
    @PostMapping("validator-mix-validated")
    public void createSampleWithSpringValidatorAndValidated(@SpringValidator({SampleDtoFirstValidator.class}) @Validated(ValidationGroups.Group1.class) @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Mix @SpringValidator & @Valid
     * If @Valid will be find errors not will be execute @SpringValidator
     */
    @PostMapping("validator-mix-valid")
    public void createSampleWithSpringValidatorAndValid(@SpringValidator({SampleDtoFirstValidator.class}) @Valid @RequestBody SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }
}
