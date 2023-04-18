package edu.ntnu.idatt2106_2023_06.backend.dto.users;

import lombok.NonNull;

public record LoginReplyDTO(@NonNull String username, @NonNull String token) {

}
