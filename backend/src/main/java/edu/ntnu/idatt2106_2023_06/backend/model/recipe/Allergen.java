package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an allergen. It, therefore, contains a ID and the display name.
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
@Table(name = "allergen")
public class Allergen {

    /**
     * The unique identifier for this allergen.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergen_id")
    private Long allergenId;

    /**
     * The name of the allergen
     */
    @Column(name = "allergen_name", nullable = false)
    @NonNull
    private String allergenName;

    /**
     * The allergens of a recipe.
     */
    @OneToMany(mappedBy = "allergen")
    @ToString.Exclude
    private Set<RecipeAllergen> recipeAllergenSet = new HashSet<>();

}
