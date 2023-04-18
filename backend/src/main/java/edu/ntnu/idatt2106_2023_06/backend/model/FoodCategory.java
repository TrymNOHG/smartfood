package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a category of food.
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
@Table(name = "food_categories")
public class FoodCategory {

    /**
     * The unique identifier for this category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * The name of the food category, must be unique and not null
     */
    @Column(name = "category_name", length = 64, nullable = false)
    @NonNull
    private String categoryName;

    /**
     * The items that use the given food category.
     */
    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Item> itemsWithCategory = new ArrayList<>();


}
