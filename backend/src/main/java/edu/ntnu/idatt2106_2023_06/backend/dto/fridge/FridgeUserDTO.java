package edu.ntnu.idatt2106_2023_06.backend.dto.fridge;

import lombok.NonNull;

public record FridgeUserDTO(@NonNull Long fridgeId,
                            @NonNull String username,
                            @NonNull boolean isSuperUser) {
}
