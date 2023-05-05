package edu.ntnu.idatt2106_2023_06.backend.model.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

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
@NoArgsConstructor(force = true)
@Entity
@Table(name = "items")
public class Item {

    /**
     * The unique identifier for this item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    @JsonIgnore
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
    @Column(name = "description")
    private String desc;

    /**
     * The store this items belongs to.
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
     * The picture of the food items, can be null
     */
    @Column(name = "picture_link")
    private String pictureLink;

    /**
     * The ean code of the item, can be null
     */
    @Column(name = "ean")
    private String ean;

    /**
     * The amount of a product.
     */
    @Column(name = "amount")
    private Double amount;

    /**
     * The Unit type of product.
     */
    @Column(name = "unit")
    private String unit;

    /**
     * The number of days this item will last in the fridge till it expires.
     */
    @Column(name = "expires_in", nullable = false)
    private int expiresIn;

    /**
     * The items in the recipe.
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private List<RecipeItems> itemsInRecipe = new ArrayList<>();

    /**
     * The items in the shopping list.
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private List<ShoppingItems> itemsInShoppingList = new ArrayList<>();

    /**
     * The items in the fridge.
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private List<FridgeItems> itemsInFridge = new ArrayList<>();

}