package edu.ntnu.idatt2106_2023_06.backend.dto.fridge;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * This data transfer object contains all the members and their corresponding information.
 * @param memberInfo    Members and their info, given as a list of FridgeMemberLoadDTO objects.
 *
 * @author Trym Hamer Gudvangen
 */
@Builder
public record FridgeMemberLoadAllDTO(@NonNull List<FridgeMemberLoadDTO> memberInfo) {
}
