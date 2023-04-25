package edu.ntnu.idatt2106_2023_06.backend.model.fridge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A class that serves as the id for the FridgeItems entity in the app.
 * It is an Embeddable class that contains two fields, items and fridge.
 *
 * @author Trym Hamer Gudvangen
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FridgeItemsId implements Serializable {

    /**
     * The id of the items.
     */
    @Column(name = "item_id")
    private Long item;

    /**
     * The id of the fridge.
     */
    @Column(name = "fridge_id")
    private Long fridge;

}
