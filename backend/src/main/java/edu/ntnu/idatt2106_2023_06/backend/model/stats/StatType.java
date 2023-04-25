package edu.ntnu.idatt2106_2023_06.backend.model.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@NoArgsConstructor(force = true)
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
    @JsonIgnore
    private String desc;

    /**
     * The items from the given store.
     */
    @OneToMany(mappedBy = "statType", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private List<Statistics> statistics = new ArrayList<>();

}
