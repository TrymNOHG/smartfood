package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.InstructionDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Instructions;

public class InstructionMapper {

    public static InstructionDTO toInstructionDTO(Instructions instruction) {
        return InstructionDTO
                .builder()
                .imageLink(instruction.getImageLink())
                .instruction(instruction.getInstruction())
                .build();
    }

}
