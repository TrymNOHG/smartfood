package edu.ntnu.idatt2106_2023_06.backend.model.validator;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SuperUserFridgeMemberValidator implements ConstraintValidator<SuperUserFridgeMemberConstraint, Fridge> {

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
