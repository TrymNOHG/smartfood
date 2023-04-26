package edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public record ShoppingItemUpdateDTO(@NonNull Long itemId, @NonNull Long fridgeId,
                                    @Nullable Integer quantity, @Nullable Boolean suggestion) {
}
