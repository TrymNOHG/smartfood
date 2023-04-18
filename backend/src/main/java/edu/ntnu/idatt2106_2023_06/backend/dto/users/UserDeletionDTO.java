package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;

//TODO do we need token??
public record UserDeletionDTO(@NonNull String username, @NonNull String userToDelete) {
}
