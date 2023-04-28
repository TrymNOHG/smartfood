package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an allergen in a recipe. It, therefore, contains a recipe part ID and AllergenRepository enum.
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
@Table(name = "recipe_allergen")
public class RecipeAllergen {

    /**
     * The composite primary key of the recipe allergens, consisting of the recipe id and the allergen.
     */
    @EmbeddedId
    private RecipeAllergenId id;

    /**
     * The recipe of the recipe allergen.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("recipe")
    @JoinColumn(name = "recipe_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Recipe recipe;

    /**
     * The allergen of the recipe allergen.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("allergen")
    @JoinColumn(name = "allergen_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Allergen allergen;

    /**
     * Amount of allergen in food, eiter TRACE or PRESENT
     */
    @Column(name = "amount")
    @Enumerated(EnumType.STRING)
    private Amount amount;

}