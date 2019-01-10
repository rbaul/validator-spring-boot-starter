package com.github.rbaul.spring.boot.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.Objects;

/**
 * Spring Bean utils
 * @author Roman Baul
 */
@Slf4j
@Service
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Get Bean from application context
     *
     * @param beanClass - class of the Bean
     * @param <T> - type of bean
     * @return - object bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return Objects.nonNull(context) ? context.getBean(beanClass) : null;
    }

    /**
     * Get Beans from application context by class type
     *
     * @param beanClass - class of the Bean
     * @param <T> - type of bean
     * @return - object beans
     */
    public static <T> Map<String, T> getBeans(Class<T> beanClass) {
        return Objects.nonNull(context) ? context.getBeansOfType(beanClass) : null;
    }

    /**
     * Get Bean from request context
     *
     * @param beanClass - class of the Bean
     * @param <T> - type of bean
     * @return - object bean
     */
    public static <T> T getRequestBean(Class<T> beanClass) {
        return isRequestScope() ? getBean(beanClass) : null;
    }

    /**
     * @return - true if it's request scope
     */
    public static boolean isRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }

}
