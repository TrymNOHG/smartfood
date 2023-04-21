package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * This data transfer object contains a list of user.
 * @param users Users to be sent, given as a {@code List<UserLoadDTO>}
 *
 * @author Trym Hamer Gudvangen
 */
@Builder
public record UserLoadAllDTO(@NonNull List<UserLoadDTO> users) {
}
