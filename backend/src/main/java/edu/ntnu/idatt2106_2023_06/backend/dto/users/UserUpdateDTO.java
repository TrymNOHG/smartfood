package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public record UserUpdateDTO(@NonNull String username, @Nullable String newUsername, @Nullable String firstName,
                            @Nullable String lastName, @Nullable String email, @Nullable byte[] picture) {
}
