package edu.ntnu.idatt2106_2023_06.backend.dto.security;

import lombok.Builder;
import lombok.NonNull;

/**
 * A data transfer object representing an authentication response with a JWT token.
 *
 * @param token The JWT token, given as a String.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
@Builder
public record AuthenticationResponseDTO(@NonNull String token) {

}
