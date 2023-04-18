package edu.ntnu.idatt2106_2023_06.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * User class represents a user of the e-commerce application with their personal information,
 * authentication details, and activities on the platform.
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

}
