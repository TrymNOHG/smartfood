package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

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
@Table(name = "recipes", indexes = {
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
    @Column(name = "description", nullable = false, length = 1000)
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
     * The thumbnail of the recipe
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

    @OneToMany(mappedBy = "recipe")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @OrderBy("recipePartId ASC")
    private List<RecipePart> recipeParts = new ArrayList<>();

    /**
     * The instructions of the recipe.
     */
    @OneToMany(mappedBy = "recipe")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @OrderBy("recipeInstructionId ASC")
    private List<Instructions> instructions = new ArrayList<>();

    /**
     * The allergens of a recipe.
     */
    @OneToMany(mappedBy = "recipe")
    @ToString.Exclude
    private Set<RecipeAllergen> recipeAllergenSet = new HashSet<>();

    /**
     * The recipe suggestions of the fridge.
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<RecipeSuggestion> recipeSuggestion = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe recipe)) return false;

        if (servingSize != recipe.servingSize) return false;
        if (difficulty != recipe.difficulty) return false;
        if (Double.compare(recipe.cookTime, cookTime) != 0) return false;
        if (!Objects.equals(recipeId, recipe.recipeId)) return false;
        if (!recipeName.equals(recipe.recipeName)) return false;
        if (!description.equals(recipe.description)) return false;
        if (!author.equals(recipe.author)) return false;
        if (!thumbnailLink.equals(recipe.thumbnailLink)) return false;
        if (!Objects.equals(recipeParts, recipe.recipeParts)) return false;
        if (!Objects.equals(instructions, recipe.instructions))
            return false;
        return Objects.equals(recipeAllergenSet, recipe.recipeAllergenSet);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = recipeId != null ? recipeId.hashCode() : 0;
        result = 31 * result + recipeName.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + servingSize;
        result = 31 * result + difficulty;
        result = 31 * result + thumbnailLink.hashCode();
        temp = Double.doubleToLongBits(cookTime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (recipeParts != null ? recipeParts.hashCode() : 0);
        result = 31 * result + (instructions != null ? instructions.hashCode() : 0);
        result = 31 * result + (recipeAllergenSet != null ? recipeAllergenSet.hashCode() : 0);
        return result;
    }
}
