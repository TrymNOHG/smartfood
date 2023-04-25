//package edu.ntnu.idatt2106_2023_06.backend.model.users;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.NonNull;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//public class Token {
//
//    /**
//     * The unique identifier for the token
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "token_id")
//    private Long tokenId;
//
//    /**
//     * The value of the token, must be non-null
//     */
//    @Column(name = "token", length = 64, nullable = false)
//    @NonNull
//    private String token;
//
//    /**
//     * The time the token was created, must be non-null
//     */
//    @Column(name = "username", length = 64, nullable = false)
//    @NonNull
//    private LocalDateTime timeCreated;
//
//
//}
