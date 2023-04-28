package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
@EqualsAndHashCode
@Table(name = "recipe", indexes = {
        @Index(columnList = "recipe_name", name = "idx_recipe_name")
})
public class Recipe {

    /**
     * The unique identifier for this items.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    /**
     * The name of the recipe
     */
    @Column(name = "recipe_name", nullable = false)
    @NonNull
    private String recipeName;

    /**
     * The description of the recipe
     */
    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    /**
     * The author of the recipe
     */
    @Column(name = "author", nullable = false)
    @NonNull
    private String author;

    /**
     * The serving size of the recipe
     */
    @Column(name = "serving_size")
    private int servingSize;


    /**
     * The difficulty of the recipe
     */
    @Column(name = "difficulty")
    private int difficulty;

    /**
     * The author of the recipe
     */
    @Column(name = "thumbnail", nullable = false)
    @NonNull
    private String thumbnailLink;

    /**
     * The time it takes to make the recipe.
     */
    @Column(name = "cook_time", nullable = false)
    @NonNull
    private double cookTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @OrderBy("recipePartId ASC")
    private List<RecipePart> recipeParts;


}
