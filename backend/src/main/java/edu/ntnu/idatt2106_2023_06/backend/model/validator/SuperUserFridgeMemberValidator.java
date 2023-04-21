package edu.ntnu.idatt2106_2023_06.backend.model.validator;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *  This class is a validator for ensuring that a fridge has at least one superuser fridge member.
 *  It implements the {@link ConstraintValidator} interface and is annotated with {@link SuperUserFridgeMemberConstraint}.
 *
 * @author Trym Hamer Gudvangen
 */
public class SuperUserFridgeMemberValidator implements ConstraintValidator<SuperUserFridgeMemberConstraint, Fridge> {

    /**
     * This method checks if a given {@link Fridge} instance has at least one superuser fridge member.
     *
     * @param fridge    The fridge instance to be validated
     * @param context   The validation context
     * @return          {@code true} if the fridge has at least one superuser fridge member or the fridge is null; otherwise, false
     */
    @Override
    public boolean isValid(Fridge fridge, ConstraintValidatorContext context) {
        if (fridge == null) {
            return true;
        }

        boolean hasSuperUser = false;
        for (FridgeMember member : fridge.getMembers()) {
            if (member.isSuperUser()) {
                hasSuperUser = true;
                break;
            }
        }

        if (!hasSuperUser) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("At least one super user fridge member is required")
                    .addPropertyNode("members").addConstraintViolation();
            return false;
        }

        return true;
    }

}
