package edu.ntnu.idatt2106_2023_06.backend.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  This annotation is used to mark a class as being validated by the {@link SuperUserFridgeMemberValidator} class.
 *  It is annotated with the {@link Constraint} annotation and targets the {@link ElementType#TYPE} element type.
 *
 * @author Trym Hamer Gudvangen
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SuperUserFridgeMemberValidator.class)
public @interface SuperUserFridgeMemberConstraint {

    /**
     * The error message to be shown if the validation fails.
     *
     * @return the error message
     */
    String message() default "At least one super user fridge member is required";

    /**
     * The groups targeted for the validation.
     *
     * @return the targeted groups
     */
    Class<?>[] groups() default {};

    /**
     * Additional metadata about the validation.
     *
     * @return the additional metadata
     */
    Class<? extends Payload>[] payload() default {};
}