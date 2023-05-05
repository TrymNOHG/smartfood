package edu.ntnu.idatt2106_2023_06.backend.model.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * This class represents a statistical entry. A stat entry contains the relevant fridge, user, statistic type,
 * value, and timestamp.
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
@Table(name = "stats")
public class Statistics {

    /**
     * The unique identifier for this statistic entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    @JsonIgnore
    private Long statId;

    /**
     * The user this statistic entry belongs to.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonIgnore
    private User user;

    /**
     * The fridge this statistic entry belongs to.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fridge_id")
    @ToString.Exclude
    @JsonIgnore
    private Fridge fridge;

    /**
     * The type of statistical entry.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "stat_type_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private StatType statType;

    /**
     * The value of the statistics.
     */
    @Column(name = "stat_value", nullable = false)
    @NonNull
    private Double statValue;

    /**
     * The name of the store of the statistics.
     */
    @Column(name = "store_name", nullable = false)
    @NonNull
    private String storeName;

    /**
     * The name of the store of the statistics.
     */
    @Column(name = "item_name", nullable = false)
    @NonNull
    private String itemName;

    /**
     * The time the entry was created.
     */
    @Column(name = "timestamp", nullable = false)
    @NonNull
    private LocalDateTime timestamp;
}
