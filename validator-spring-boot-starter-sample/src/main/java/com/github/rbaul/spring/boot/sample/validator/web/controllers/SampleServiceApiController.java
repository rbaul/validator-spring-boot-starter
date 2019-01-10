package com.github.rbaul.spring.boot.sample.validator.web.controllers;

import com.github.rbaul.spring.boot.sample.validator.service.SampleServiceImpl;
import com.github.rbaul.spring.boot.sample.validator.web.dtos.SampleDto;
import com.github.rbaul.spring.boot.sample.validator.web.validators.SampleDtoFirstValidator;
import com.github.rbaul.spring.boot.sample.validator.web.validators.SampleDtoSecondValidator;
import com.github.rbaul.spring.boot.sample.validator.web.validators.ValidationGroups;
import com.github.rbaul.spring.boot.validator.SpringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("service")
@RestController
public class SampleServiceApiController {

    private final SampleServiceImpl sampleService;

    @PostMapping
    public void createSampleNoValidate(@RequestBody SampleDto sampleDto){
        sampleService.createSampleNoValidate(sampleDto);
    }

    /**
     * Validate with all supported validators (except constraints)
     */
    @PostMapping("validator-with-all-validators")
    public void createSampleWithSpringValidatorAll(@RequestBody SampleDto sampleDto){
        sampleService.createSampleWithSpringValidatorAll(sampleDto);
    }

    /**
     * Validate with all supported validators (include constraints before)
     */
    @PostMapping("validator-with-all-validators-contstraint")
    public void createSampleWithConstraintSpringValidatorAll(@RequestBody SampleDto sampleDto){
        sampleService.createSampleWithConstraintSpringValidatorAll(sampleDto);
    }

    /**
     * Validate with specific validators
     */
    @PostMapping("validator")
    public void createSampleWithSpringValidator(@RequestBody SampleDto sampleDto){
        sampleService.createSampleWithSpringValidator(sampleDto);
    }

    /**
     * Validate with specific groups
     */
    @PostMapping("validator-group")
    public void createSampleWithSpringValidatorGroups(@RequestBody SampleDto sampleDto){
        sampleService.createSampleWithSpringValidatorGroups(sampleDto);
    }

    /**
     * Validate with specific validators and groups
     */
    @PostMapping("validator-group-mix")
    public void createSampleWithSpringValidatorGroupsMix(@RequestBody SampleDto sampleDto){
        sampleService.createSampleWithSpringValidatorGroupsMix(sampleDto);
    }

}
