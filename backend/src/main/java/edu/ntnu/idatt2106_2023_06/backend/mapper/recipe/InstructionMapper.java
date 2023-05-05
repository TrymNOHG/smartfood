package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.InstructionDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Instructions;

/**

 The InstructionMapper class contains methods for mapping between Instructions model and DTO.
 */
public class InstructionMapper {

    /**
     * Maps Instructions object to InstructionDTO object.
     *
     * @param instruction an Instructions object to map.
     * @return the mapped InstructionDTO object.
     */
    public static InstructionDTO toInstructionDTO(Instructions instruction) {
        return InstructionDTO
                .builder()
                .imageLink(instruction.getImageLink())
                .instruction(instruction.getInstruction())
                .build();
    }

}
