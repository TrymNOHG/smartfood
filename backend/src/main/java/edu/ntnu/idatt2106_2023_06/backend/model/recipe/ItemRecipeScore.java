package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

/**
 * This class represents the score between a given item and a recipe. The score represents the relevance the item has to
 * the recipe, whether it is in the recipe (1) or whether it is not (0). It may be uncertain, which is where the range
 * comes in.
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
@Table(name = "item_recipe_score")
public class ItemRecipeScore {

    /**
     * The composite primary key of the item recipe score, consisting of the item id and the recipe id.
     */
    @EmbeddedId
    private ItemRecipeScoreId itemRecipeScoreId;

    /**
     * The item.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("item")
    @JoinColumn(name = "item_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Item item;

    /**
     * The recipe.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("recipe")
    @JoinColumn(name = "recipe_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Recipe recipe;

    /**
     * This relevance score of the item to the given recipe.
     */
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "1.0", inclusive = true)
    @Column(name = "score")
    private double score;


}
