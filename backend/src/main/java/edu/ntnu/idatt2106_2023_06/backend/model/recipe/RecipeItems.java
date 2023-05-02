package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an items in a recipe. It, therefore, contains a recipe part ID and the items ID.
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
     * The composite primary key of the recipe, consisting of the items id and the recipe part id.
     */
    @EmbeddedId
    private RecipeItemId id;

    /**
     * The items of the recipe.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("items")
    @JoinColumn(name = "item_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Item item;

    /**
     * The recipe of the recipe items.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("recipePart")
    @JoinColumn(name = "recipe_part_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private RecipePart recipePart;

    /**
     * This is the quantity of the items
     */
    @Column(name = "quantity")
    private double quantity;

    /**
     * The unit of measurement of the item.
     */
    @Column(name = "unit", nullable = false)
    @NonNull
    private String unitOfMeasurement;


}
