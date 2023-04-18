package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents a recipe. It, therefore, contains a recipe ID and the time it takes to create the meal.
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
@Table(name = "recipe")
public class Recipe {

    /**
     * The unique identifier for this item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    /**
     * The time it takes to make the recipe.
     */
    @Column(name = "cook_time", nullable = false)
    @NonNull
    private double cookTime;

}
