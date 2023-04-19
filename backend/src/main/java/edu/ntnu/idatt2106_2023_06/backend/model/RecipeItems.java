package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an item in a recipe. It, therefore, contains a recipe ID and the item ID.
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
@Table(name = "recipe_items")
public class RecipeItems {

    /**
     * The composite primary key of the recipe, consisting of the item id and the recipe id.
     */
    @EmbeddedId
    private RecipeItemId id;

    /**
     * The item of the recipe.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("item")
    @JoinColumn(name = "item_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Item item;

    /**
     * The recipe of the recipe item.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @MapsId("recipe")
    @JoinColumn(name = "recipe_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Recipe recipe;


}
