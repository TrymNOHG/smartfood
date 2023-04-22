package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the different types of statistics saved.
 *
 * @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "stat_types")
public class StatType {

    /**
     * The unique identifier for this type of statistic.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_type_id")
    private Long statTypeId;

    /**
     * The name of the type of statistics, not null
     */
    @Column(name = "type_name", length = 30, nullable = false)
    @NonNull
    private String typeName;

    /**
     * The description of the type of statistics
     */
    @Column(name = "description")
    private String desc;

    /**
     * The items from the given store.
     */
    @OneToMany(mappedBy = "statType", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Statistics> itemsInStore = new ArrayList<>();

}
