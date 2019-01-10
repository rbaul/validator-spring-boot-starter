package com.github.rbaul.spring.boot.validator;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AspectJ to intercept {@link SpringValidator} parameters.
 *
 * @author Roman Baul
 */
@Slf4j
@Aspect
@Component
public class SpringValidatorInterceptor {

    /**
     * Pointcut of parameters with {@link SpringValidator}
     */
    @Pointcut("execution(public * *(.., @SpringValidator (*), ..))")
    protected void springValidatorAnnotationOnParamaeter() {
    }

    /**
     * Process of {@link SpringValidator}
     * @param joinPoint Provides reflective access
     * @throws MethodArgumentNotValidException errors
     */
    @Before(value = "springValidatorAnnotationOnParamaeter()")
    public void processSpringValidatorAnnotation(JoinPoint joinPoint) throws MethodArgumentNotValidException {
        Object[] methodParameterValues = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        for (int i = 0; i < method.getParameterCount(); i++) {
            MethodParameter methodParameter = new MethodParameter(method, i);
            Object methodParameterValue = methodParameterValues[i];

            Annotation[] parameterAnnotations = methodParameter.getParameterAnnotations();

            for (Annotation parameterAnnotation : parameterAnnotations) {
                SpringValidator springValidatorAnnotation = AnnotationUtils.getAnnotation(parameterAnnotation, SpringValidator.class);
                if(springValidatorAnnotation != null){

                    Set<Validator> processValidators = new HashSet<>();

                    Class<? extends Validator>[] validators = springValidatorAnnotation.validators();
                    Class<?>[] groups = springValidatorAnnotation.groups();
                    boolean validateConstraintBefore = springValidatorAnnotation.validateConstraintBefore();
                    boolean firstValidatorThrow = springValidatorAnnotation.firstValidatorThrow();
                    boolean firstGroupThrow = springValidatorAnnotation.firstGroupThrow();

                    // Constraint validate process
                    if(validateConstraintBefore){
//                    ApplicationContextAware.class
//                    LocalValidatorFactoryBean.class;
//                    SpringValidatorAdapter.class;
//                    WebMvcValidator.class;
                        LocalValidatorFactoryBean constraintValidator = SpringBeanUtils.getBean(LocalValidatorFactoryBean.class);
                        processValidations(methodParameter, methodParameterValue, new HashSet<>(Collections.singletonList(constraintValidator)));
                    }

                    // No mention validators
                    if(validators.length == 0 && groups.length == 0){
                        Map<String, Validator> beans = SpringBeanUtils.getBeans(Validator.class);
                        if (beans != null) {
                            for (Validator validator : beans.values()) {
                                // Exclude Local and Web
                                if(validator.supports(methodParameterValue.getClass()) && !(validator instanceof ApplicationContextAware)){
                                    if(firstValidatorThrow){
                                        processValidations(methodParameter, methodParameterValue, new HashSet<>(Collections.singletonList(validator)));
                                    }
                                    processValidators.add(validator);
                                }
                            }
                        }
                    } else {

                        // Validator process
                        for (Class<? extends Validator> aClass : validators) {
                            Validator validator = SpringBeanUtils.getBean(aClass);
                            if (firstValidatorThrow) {
                                processValidations(methodParameter, methodParameterValue, new HashSet<>(Collections.singletonList(validator)));
                            }
                            processValidators.add(validator);
                        }

                        // Groups process
                        for (Class<?> group : groups) {
                            Set<Validator> groupValidators = new HashSet<>();
                            Map<String, ?> beans = SpringBeanUtils.getBeans(group);
                            if (beans != null) {
                                for (Object value : beans.values()) {
                                    if (value instanceof Validator) {
                                        Validator validator = (Validator) value;
                                        if (firstValidatorThrow) {
                                            processValidations(methodParameter, methodParameterValue, new HashSet<>(Collections.singletonList(validator)));
                                        }
                                        processValidators.add(validator);
                                        groupValidators.add(validator);
                                    } else {
                                        log.error("This bean not of type Validator '{}'", value.getClass());
                                    }
                                }
                            }
                            if (firstGroupThrow) {
                                processValidations(methodParameter, methodParameterValue, groupValidators);
                            }
                        }
                    }

                    if(!firstValidatorThrow) {
                        processValidations(methodParameter, methodParameterValue, processValidators);
                    }

                }
            }
        }
    }

    /**
     * Process Validation
     * @param methodParameter parameter
     * @param methodParameterValue value of parameter
     * @param validators set of validators
     * @throws MethodArgumentNotValidException errors
     */
    private void processValidations(MethodParameter methodParameter, Object methodParameterValue, Set<Validator> validators) throws MethodArgumentNotValidException {
        String variableNameForParameter = Conventions.getVariableNameForParameter(methodParameter);
        BindingResult result = new BeanPropertyBindingResult(methodParameterValue, variableNameForParameter);

        for (Validator validator : validators) {
            if(validator.supports(methodParameterValue.getClass())){
                validator.validate(methodParameterValue, result);
            } else {
                log.error("Validator '{}' not support parameter: '{}'", validator.getClass(), methodParameterValue.getClass());
            }
        }

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(methodParameter, result);
        }
    }

}
