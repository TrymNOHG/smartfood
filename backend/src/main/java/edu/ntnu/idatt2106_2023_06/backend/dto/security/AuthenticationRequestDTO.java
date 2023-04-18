package edu.ntnu.idatt2106_2023_06.backend.dto.security;

import lombok.*;

public record AuthenticationRequestDTO(@NonNull String email, @NonNull String password) {

}