package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 *  A record representing the data needed to register a new user.
 *  Contains the user's chosen username, password, first name, last name, and email.
 *  @param username the user's chosen username
 *  @param password the user's chosen password
 *  @param firstName the user's first name
 *  @param lastName the user's last name
 *  @param email the user's email address
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public record UserRegisterDTO(@NonNull String username, @NonNull String password, @NonNull String firstName,
                              @NonNull String lastName, @NonNull String email) {
}
