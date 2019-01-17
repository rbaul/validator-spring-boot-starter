# Validator Spring Boot Starter
[Spring Boot](https://spring.io/projects/spring-boot) Starter for `@SpringValidator`.
> Spring Boot `@SpringValidator` annotation that will help validate arguments more easily. 

[![Build Status](https://travis-ci.com/rbaul/validator-spring-boot-starter.svg?branch=master)](https://travis-ci.com/rbaul/validator-spring-boot-starter)
[![Sonatype Nexus (Snapshots) badge](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.github.rbaul/validator-spring-boot-starter.svg)](https://oss.sonatype.org/#nexus-search;quick~validator-spring-boot-starter)
[![CodeFactor](https://www.codefactor.io/repository/github/rbaul/validator-spring-boot-starter/badge)](https://www.codefactor.io/repository/github/rbaul/validator-spring-boot-starter)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Features
Easy way of validation any method's arguments with using [Spring’s Validator](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/Validator.html) interface

> Choose specifics `Validator` for validation

> Validate by Groups

> Can be use with `Valid`/`Validated` for initial validation (no double checks)

> No need `InitBinder`/ registration of validator

> Validate Object Constraints validation before validation

> Controller/Service layer

## Build on
>* Java 8
>* Spring Boot 1.5.18

## Setup
###### Maven dependency
```xml
<repositories>
    <repository>
        <id>Sonatype</id>
        <name>Sonatype's repository</name>
        <url>https://oss.sonatype.org/content/groups/public/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.rbaul</groupId>
    <artifactId>validator-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

###### Gradle dependency
```groovy
repositories {
    maven { url "https://oss.sonatype.org/content/groups/public" }
}

compile 'com.github.rbaul:validator-spring-boot-starter:1.0.0-SNAPSHOT'
```

## Precondition
> All `Validator`'s must be `Bean`s

> Public methods

> `Spring AOP Proxy` conditions

##### Validator Example 
```java
@Component
public class SampleDtoFirstValidator implements Validator, ValidationGroups.Group2, ValidationGroups.Group1 {
    @Override
    public boolean supports(Class<?> clazz) {
        return SampleDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "id", "validator.sampleDto.id.null");
    }
}
```

##### Group Example 
```java
public final class ValidationGroups {
    public interface Group1 {
    }

    public interface Group2 {
    }

    public interface Group3 {
    }
}
```

## Usage
See all examples in [validator-spring-boot-starter-sample]()

##### Validate with all supported Validators of argument type
```java
    @PostMapping("validator-with-all-validators")
    public void createSampleWithSpringValidatorAll(@SpringValidator @RequestBody SampleDto sampleDto){
        ...
    }
```

##### Validate with all supported Validators of argument type and Constraints validators before
```java
    @PostMapping("validator-with-all-validators-contstraint")
    public void createSampleWithConstraintSpringValidatorAll(@SpringValidator(validateConstraintBefore = true) @RequestBody SampleDto sampleDto){
        ...
    }
```
Can be achieve also same with `@Valid` or `@Validated` 
```java
    @PostMapping("validator-with-all-validators-contstraint")
    public void createSampleWithConstraintSpringValidatorAll(@SpringValidator @Valid @RequestBody SampleDto sampleDto){
        ...
    }
```

##### Validate with specific Validators
```java
    @PostMapping("validator")
    public void createSampleWithSpringValidator(@SpringValidator({SampleDtoFirstValidator.class, SampleDtoSecondValidator.class}) @RequestBody SampleDto sampleDto){
       ...
    }
```

##### Validate with specific Groups
```java
    @PostMapping("validator-group")
    public void createSampleWithSpringValidatorGroups(@SpringValidator(groups = {ValidationGroups.Group1.class, ValidationGroups.Group2.class}) @RequestBody SampleDto sampleDto){
        ...
    }
```

##### Validate with specific Groups and specific Validators
```java
    @PostMapping("validator-group-mix")
    public void createSampleWithSpringValidatorGroupsMix(@SpringValidator(validators = {SampleDtoFirstValidator.class}, groups = {ValidationGroups.Group1.class, ValidationGroups.Group2.class}) @RequestBody SampleDto sampleDto){
        ...
    }
```

##### Validate With `@Validated`
```java
    @PostMapping("validator-mix-validated")
    public void createSampleWithSpringValidatorAndValidated(@SpringValidator(groups = ValidationGroups.Group1.class) @Validated(ValidationGroups.Group1.class) @RequestBody SampleDto sampleDto){
        ...
    }
```

## Run Sample
> Run `ValidatorSpringBootStarterSampleApplication.java` from `validator-spring-boot-starter-sample`

Follow [http://localhost:8080/swagger-ui.html]()

## License

Licensed under the [Apache License, Version 2.0].  

[Apache License, Version 2.0]: LICENSE