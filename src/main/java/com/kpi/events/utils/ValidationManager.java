package com.kpi.events.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class ValidationManager {
    private static Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public static <T> boolean isValidate(T object) {
        Validator validator = getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        return constraintViolations.isEmpty();
    }

    public static <T> String getFirsErrorMessage(T object) {
        Validator validator = getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        return String.valueOf(constraintViolations.iterator().next().getMessage());
    }
}
