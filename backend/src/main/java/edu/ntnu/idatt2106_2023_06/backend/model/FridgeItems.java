package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an items in the fridge. It, therefore, contains a fridge ID and the items ID.
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
@Table(name = "fridge_items")
public class FridgeItems {

    /**
     * The composite primary key of the fridge items, consisting of the items id and the fridge id.
     */
    @EmbeddedId
    private FridgeItemsId id;

    /**
     * The items of the fridge items.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("items")
    @JoinColumn(name = "item_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Item item;

    /**
     * The fridge of the fridge items.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("fridge")
    @JoinColumn(name = "fridge_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Fridge fridge;

    /**
     * This is the quantity of the items
     */
    @Column(name = "quantity")
    private int quantity;



}
