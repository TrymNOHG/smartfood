package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;

/**
 *  A data transfer object (DTO) representing a user login request.
 *
 * @param email     Email of the user logging in, given as a String
 * @param password  Password of the user, given as a String.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public record UserLoginDTO(@NonNull String email, @NonNull String password) {

}
