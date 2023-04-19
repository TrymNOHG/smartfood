package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents a shopping list. It, therefore, contains a fridge ID (the shopping list's id) and the items
 * id.
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
@Table(name = "shopping_items")
public class ShoppingItems {

    /**
     * The composite primary key of the shopping list items, consisting of the items id and the fridge id.
     */
    @EmbeddedId
    private FridgeItemsId id;

    /**
     * The items for the shopping list.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("items")
    @JoinColumn(name = "item_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Item item;

    /**
     * The fridge of the shopping list.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @MapsId("fridge")
    @JoinColumn(name = "fridge_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Fridge fridge;

    /**
     * Is the items a suggestion.
     */
    @Column(name = "suggestion")
    @NonNull
    private boolean suggestion;


    /**
     * This is the quantity of the items
     */
    @Column(name = "quantity")
    private int quantity;


}
