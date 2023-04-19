package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdateDTO(@Nullable String username, @Nullable String firstName,
                            @Nullable String lastName, @Nullable String email, @Nullable MultipartFile picture) {
}