package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an instruction for a recipe.
 * It, therefore, contains a recipe ID and the time it takes to create the meal.
 *
 * @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "instructions")
public class Instructions {

    /**
     * The unique identifier for this recipe part instruction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_instruction_id")
    private Long recipeInstructionId;


    /**
     * The recipe the instructions are a part of.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Recipe recipe;


    /**
     * The actual instruction.
     */
    @Column(name = "instruction", nullable = false)
    @NonNull
    private String instruction;

    /**
     * The link to an image if there is one.
     */
    @Column(name = "image_link", nullable = false)
    @NonNull
    private String imageLink;

}
