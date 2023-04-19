package edu.ntnu.idatt2106_2023_06.backend.dto.security;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record AuthenticationResponseDTO(@NonNull String token) {

}
