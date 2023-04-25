package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.illegal.IllegalStatTypeException;
import edu.ntnu.idatt2106_2023_06.backend.exception.illegal.IllegalStatValueException;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.StatNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.StatMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatService implements IStatService {

    private final JwtService jwtService;
    private final StatRepository statRepository;

    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final StatTypeRepository statTypeRepository;

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

        User user = userRepository.findById(statDeleteFromFridgeDTO.userId()).orElseThrow(
                () -> new UserNotFoundException(statDeleteFromFridgeDTO.userId())
        );
        Fridge fridge = fridgeRepository.findById(statDeleteFromFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statDeleteFromFridgeDTO.fridgeId())
        );
        StatType statType1 = statTypeRepository.findById(1L).orElseThrow(
                () -> new StatNotFoundException(1L)
        );
        StatType statType2 = statTypeRepository.findById(2L).orElseThrow(
                () -> new StatNotFoundException(2L)
        );

        statRepository.saveAll(StatMapper.toStatistics(statDeleteFromFridgeDTO, user, fridge, statType1, statType2));
    }

    @Override
    public void statAddItemToFridge(StatAddItemToFridgeDTO statAddItemToFridgeDTO) {
        Long userId = jwtService.getAuthenticatedUserId();
        if(!Objects.equals(userId, statAddItemToFridgeDTO.userId())) {
            throw new UnauthorizedException(userId.toString());
        }

        checkValidStatValue(statAddItemToFridgeDTO.price(), 3);

        User user = userRepository.findById(statAddItemToFridgeDTO.userId()).orElseThrow(
                () -> new UserNotFoundException(statAddItemToFridgeDTO.userId())
        );
        Fridge fridge = fridgeRepository.findById(statAddItemToFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statAddItemToFridgeDTO.fridgeId())
        );
        StatType statType = statTypeRepository.findById(3L).orElseThrow(
                () -> new StatNotFoundException(3L)
        );

        statRepository.saveAll(StatMapper.toStatistics(statAddItemToFridgeDTO, user, fridge, statType));
    }


}
