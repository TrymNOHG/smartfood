package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Builder
public record UserLoadDTO(@NonNull Long userId, @NonNull String username, @NonNull String firstName,
                          @NonNull String lastName, @NonNull String email) {
}
