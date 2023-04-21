package edu.ntnu.idatt2106_2023_06.backend.dto.fridge;

import lombok.Builder;
import lombok.NonNull;

/**
 * A DTO class representing the relationship between a user and a fridge.
 * It contains information about the fridge id, the username of the user, and if the user is a superuser or not.
 * @param fridgeId      ID of the fridge, given as a Long object.
 * @param username      Username of the user, given as a String
 * @param isSuperUser   Status of whether user is superuser {@code true} or not {@code false}
 *
 * @author Trym Hamer Gudvangen
 */
@Builder
public record FridgeUserDTO(@NonNull Long fridgeId,
                            @NonNull String username,
                            @NonNull boolean isSuperUser) {
}
