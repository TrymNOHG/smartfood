package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Builder
public record UserLoadDTO(@NonNull Long userId, @NonNull String username,
                          @NonNull String fullName, @NonNull String email,
                          @Nullable Date birthDate, @Nullable String phone,
                          @Nullable byte[] picture) {
}
