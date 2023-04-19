package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents a user who is a member of a fridge. It, therefore, contains a fridge ID and the user ID.
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
@Table(name = "fridge_members")
public class FridgeMember {


    /**
     * The composite primary key of the fridge member, consisting of the user id and the fridge id.
     */
    @EmbeddedId
    private FridgeMemberId id;

    /**
     * The user of the fridge.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("user")
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private User user;

    /**
     * The fridge that the user is a member of.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("fridge")
    @JoinColumn(name = "fridge_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Fridge fridge;



}
