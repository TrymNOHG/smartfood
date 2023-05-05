package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * This record represents a DTO for searching up a user.
 * @param userId    The ID of the user.
 * @param username  The name of the user.
 */
@Builder
public record UserSearchDTO(@NonNull Long userId, @NonNull String username) {
}
