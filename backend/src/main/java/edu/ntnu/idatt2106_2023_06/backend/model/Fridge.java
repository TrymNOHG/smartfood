package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.validator.SuperUserFridgeMemberConstraint;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Fridge class represents a common storage of food items in the application. It can, therefore, include dry, frozen, or
 * wet items. It resembles more as a common gathering of people and food than a literal fridge.
 *
 *  @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "fridge")
@SuperUserFridgeMemberConstraint
public class Fridge {

    /**
     * The unique identifier for the fridge
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    private Long fridgeId;

    /**
     * The name of the fridge, must be unique and not null
     */
    @Column(name = "fridge_name", length = 64, nullable = false)
    @NonNull
    private String fridgeName;

    /**
     * The members of the fridge.
     */
    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FridgeMember> members = new HashSet<>();

}
