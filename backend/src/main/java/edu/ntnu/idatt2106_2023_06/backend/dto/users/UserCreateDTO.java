package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public record UserCreateDTO(@NonNull String username, @NonNull String password, @NonNull String firstName,
                            @NonNull String lastName, @NonNull String email, @Nullable Date birthDate,
                            @Nullable String phone, @Nullable byte[] picture) {
}
