package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents a shopping list. It, therefore, contains a fridge ID (the shopping list's id) and the item
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
     * The composite primary key of the shopping list item, consisting of the item id and the fridge id.
     */
    @EmbeddedId
    private FridgeItemsId id;

    /**
     * The item for the shopping list.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("item")
    @JoinColumn(name = "item_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Item item;

    /**
     * The fridge of the shopping list.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("fridge")
    @JoinColumn(name = "fridge_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Fridge fridge;

    /**
     * Is the item a suggestion.
     */
    @Column(name = "suggestion")
    @NonNull
    private boolean suggestion;


    /**
     * This is the quantity of the item
     */
    @Column(name = "quantity")
    private int quantity;


}
