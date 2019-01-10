package com.github.rbaul.spring.boot.sample.validator.service;

import com.github.rbaul.spring.boot.sample.validator.web.dtos.SampleDto;
import com.github.rbaul.spring.boot.sample.validator.web.validators.SampleDtoFirstValidator;
import com.github.rbaul.spring.boot.sample.validator.web.validators.SampleDtoSecondValidator;
import com.github.rbaul.spring.boot.sample.validator.web.validators.ValidationGroups;
import com.github.rbaul.spring.boot.validator.SpringValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleServiceImpl {
    public void createSampleNoValidate(SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with all supported validators (except constraints)
     */
    public void createSampleWithSpringValidatorAll(@SpringValidator SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with all supported validators (include constraints before)
     */
    public void createSampleWithConstraintSpringValidatorAll(@SpringValidator(validateConstraintBefore = true) SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with specific validators
     */
    public void createSampleWithSpringValidator(@SpringValidator({SampleDtoFirstValidator.class, SampleDtoSecondValidator.class}) SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with specific groups
     */
    public void createSampleWithSpringValidatorGroups(@SpringValidator(groups = {ValidationGroups.Group1.class, ValidationGroups.Group2.class}) SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

    /**
     * Validate with specific validators and groups
     */
    public void createSampleWithSpringValidatorGroupsMix(@SpringValidator(validators = {SampleDtoFirstValidator.class}, groups = {ValidationGroups.Group1.class, ValidationGroups.Group2.class}) SampleDto sampleDto){
        log.info("Sample created: {}", sampleDto);
    }

}
