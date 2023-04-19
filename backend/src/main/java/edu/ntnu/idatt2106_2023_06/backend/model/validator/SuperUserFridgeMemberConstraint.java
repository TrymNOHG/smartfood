package edu.ntnu.idatt2106_2023_06.backend.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SuperUserFridgeMemberValidator.class)
public @interface SuperUserFridgeMemberConstraint {
    String message() default "At least one super user fridge member is required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}