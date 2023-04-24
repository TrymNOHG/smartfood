package edu.ntnu.idatt2106_2023_06.backend.model.users;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * This class represents a user token. A token has its value, time of creation, expiration, and confirmation, and
 * the user connected.
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
@Table(name = "tokens")
public class Token {

    /**
     * The unique identifier for the token
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    /**
     * The value of the token, must be non-null
     */
    @Column(name = "token", length = 64, nullable = false)
    @NonNull
    private String token;

    /**
     * The time the token was created, must be non-null
     */
    @Column(name = "time_created", length = 64, nullable = false)
    @NonNull
    private LocalDateTime timeCreated;

    /**
     * The time the token expires, must be non-null
     */
    @Column(name = "time_expires", length = 64, nullable = false)
    @NonNull
    private LocalDateTime timeExpires;

    /**
     * The time the token was confirmed
     */
    @Column(name = "time_confirmed", length = 64)
    private LocalDateTime timeConfirmed;

    /**
     * The user of the fridge.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("user")
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private User user;


}
