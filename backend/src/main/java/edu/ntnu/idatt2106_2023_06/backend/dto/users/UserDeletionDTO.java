package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;

/**
 * A data transfer object class representing the request to delete a user from the system.
 *
 * @param username      The username of the user to be deleted, given as a String.
 * @param userToDelete  The username of the person trying to delete, given as a String.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public record UserDeletionDTO(@NonNull String username, @NonNull String userToDelete) {
}
