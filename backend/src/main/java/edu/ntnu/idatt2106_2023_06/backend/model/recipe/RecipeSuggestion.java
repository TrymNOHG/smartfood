package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import jakarta.persistence.*;
import lombok.*;


/**
 * This class represents a recipe suggestion.
 *
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recipe_suggestion")
public class RecipeSuggestion {
    /**
     * The composite primary key of the recipe suggestion.
     */
    @EmbeddedId
    private RecipeSuggestionId id;

    /**
     * The recipe of the suggestion.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("recipes")
    @JoinColumn(name = "recipe_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Recipe recipe;

    /**
     * The fridge of the suggestion.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("fridge")
    @JoinColumn(name = "fridge_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Fridge fridge;

    /**
     * The user of the suggestion.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("users")
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private User user;

}
