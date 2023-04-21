package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;

/**
 * A data transfer object (DTO) class representing the data required to update a user's password.
 *  It contains the user's old password and their desired new password.
 *
 * @param oldPassword Prior password, given as a String
 * @param newPassword New password, given as a String
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public record UserPasswordUpdateDTO (@NonNull String oldPassword, @NonNull String newPassword) {
}
