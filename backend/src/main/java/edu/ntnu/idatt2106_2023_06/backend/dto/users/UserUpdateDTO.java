package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 *  This record contains a DTO for updating a user's information.
 *  It contains the user's updated username, first name, last name, and email.
 *  @param username     New username
 *  @param firstName    New first name
 *  @param lastName     New last name
 *  @param email        New email
 *
 *  Note: This DTO does not include a password field as it should never be updated directly.
 *  If a user wants to change their password, they should use the changePassword endpoint.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public record UserUpdateDTO(@Nullable String username, @Nullable String firstName,
                            @Nullable String lastName, @Nullable String email) {
}