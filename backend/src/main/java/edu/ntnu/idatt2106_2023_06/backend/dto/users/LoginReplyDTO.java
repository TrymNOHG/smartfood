package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;

/**
 * A data transfer object representing a response for user login.
 *
 * @param username The username of the user, given as a String
 * @param token    The token associated, given as a String.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public record LoginReplyDTO(@NonNull String username, @NonNull String token) {

}
