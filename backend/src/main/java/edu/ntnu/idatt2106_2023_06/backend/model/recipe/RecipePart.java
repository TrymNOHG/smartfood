package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a part of a recipe.
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
@Table(name = "recipe_part")
public class RecipePart {

    /**
     * The unique identifier for this recipe part.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_part_id")
    private Long recipePartId;


    /**
     * The recipe of the recipe items.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Recipe recipe;

    /**
     * The name of the part of the recipe
     */
    @Column(name = "part_name", nullable = false)
    @NonNull
    private String partName;

    /**
     * The items in the recipe part.
     */
    @OneToMany(mappedBy = "recipePart", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<RecipeItems> itemsInRecipe = new ArrayList<>();

}
