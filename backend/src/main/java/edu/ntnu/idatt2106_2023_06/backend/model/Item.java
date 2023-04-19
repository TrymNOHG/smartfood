package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * This class represents a food items. A food items has an id, name, brief description, price, purchase and expiration date,
 * and category.
 *
 * @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    /**
     * The unique identifier for this items.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    /**
     * The name of the food product, must be unique and not null
     */
    @Column(name = "product_name", length = 64, nullable = false)
    @NonNull
    private String productName;

    /**
     * A brief description of this items.
     */
    @Column(name = "brief_desc", nullable = false)
    @NonNull
    private String briefDesc;

    /**
     * The category this items belongs to.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    @NonNull
    @ToString.Exclude
    private Store store;

    /**
     * The price of this items.
     */
    @Column(name = "price", nullable = false)
    @NonNull
    private double price;

    /**
     * The purchase date of the items, can be null
     */
    @Column(name = "purchase_date")
    private Date purchaseDate;

    /**
     * The expiration date of the items, can be null
     */
    @Column(name = "expiration_date")
    private Date expirationDate;

    /**
     * The picture of the food items, can be null
     */
    @Column(name = "picture_link")
    private String pictureLink;


}