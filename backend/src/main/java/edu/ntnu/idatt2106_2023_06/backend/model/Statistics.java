package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
@NoArgsConstructor
@Entity
@Table(name = "stats")
public class Statistics {

    /**
     * The unique identifier for this statistic entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private Long statId;

    /**
     * The user this statistic entry belongs to.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    /**
     * The fridge this statistic entry belongs to.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fridge_id")
    @ToString.Exclude
    private Fridge fridge;

    /**
     * The type of statistical entry.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "stat_type_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private StatType statType;

    /**
     * The value of the statistics.
     */
    @Column(name = "stat_value", nullable = false)
    @NonNull
    private int statValue;

    /**
     * The time the entry was created.
     */
    @Column(name = "timestamp", nullable = false)
    @NonNull
    private Timestamp timestamp;

}
