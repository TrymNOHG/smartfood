package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.IllegalStatTypeException;
import edu.ntnu.idatt2106_2023_06.backend.exception.IllegalStatValueException;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.StatMapper;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatService implements IStatService {

    private final JwtService jwtService;
    private final StatRepository statRepository;
    private final StatMapper statMapper;

    private void checkValidStatValue(int statValue, int statType) {
        switch (statType) {
            case 1 -> {
                if (statValue < 0 || statValue > 100) {
                    throw new IllegalStatValueException(0, 100);
                }
            }
            case 2, 3 -> {
                if (statValue < 0) {
                    throw new IllegalStatValueException();
                }
            }
            default -> throw new IllegalStatTypeException(statType);
        }
    }

    @Override
    public void statDeleteItemFromFridge(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO) {
        Long userId = jwtService.getAuthenticatedUserId();
        if(!Objects.equals(userId, statDeleteFromFridgeDTO.userId())) {
            throw new UnauthorizedException(userId.toString());
        }

        checkValidStatValue(statDeleteFromFridgeDTO.price(), 2);
        checkValidStatValue(statDeleteFromFridgeDTO.percentageThrown(), 1);

        statRepository.saveAll(statMapper.toStatistics(statDeleteFromFridgeDTO));
    }

    @Override
    public void statAddItemToFridge(StatAddItemToFridgeDTO statAddItemToFridgeDTO) {
        Long userId = jwtService.getAuthenticatedUserId();
        if(!Objects.equals(userId, statAddItemToFridgeDTO.userId())) {
            throw new UnauthorizedException(userId.toString());
        }

        checkValidStatValue(statAddItemToFridgeDTO.price(), 3);

        statRepository.saveAll(statMapper.toStatistics(statAddItemToFridgeDTO));
    }


}
