package edu.ntnu.idatt2106_2023_06.backend.model.fridge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents an items in the fridge. It, therefore, contains a fridge ID and the items ID.
 *
 * @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fridge_items")
public class FridgeItems {

    /**
     * The composite primary key of the fridge items, consisting of the items id and the fridge id.
     */
    @EmbeddedId
    @JsonIgnore
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
    @JsonIgnore
    private Fridge fridge;

    /**
     * This is the amount of the fridge item
     */
    @Column(name = "amount")
    private double amount;

    /**
     * The purchase date of the items, can be null
     */
    @Column(name = "purchase_date", nullable = false)
    @NonNull
    private LocalDateTime purchaseDate;

    /**
     * The expiration date of the items, can be null
     */
    @Column(name = "expiration_date", nullable = false)
    @NonNull
    private LocalDateTime expirationDate;

    /**
     * The notification of the fridge items.
     */
    @OneToMany(mappedBy = "fridgeItem", cascade = CascadeType.ALL)
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Notification> notification = new HashSet<>();
}
