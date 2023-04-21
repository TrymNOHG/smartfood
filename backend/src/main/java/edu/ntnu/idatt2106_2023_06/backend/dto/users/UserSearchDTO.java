package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Builder
public record UserSearchDTO(@NonNull Long userId, @NonNull String username) {
}
