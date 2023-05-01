package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService implements IStatService {

    private final Logger logger = LoggerFactory.getLogger(StatService.class);

    private final JwtService jwtService;
    private final FridgeService fridgeService;

    private final StatRepository statRepository;
    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final StatTypeRepository statTypeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private void checkValidStatValue(double statValue, int statType) {
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
        Long userId = checkUserIsAuthenticated();

        checkValidStatValue(statDeleteFromFridgeDTO.price(), 2);
        checkValidStatValue(statDeleteFromFridgeDTO.percentageThrown(), 1);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
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
        Long userId = checkUserIsAuthenticated();

        checkValidStatValue(statAddItemToFridgeDTO.price(), 3);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Fridge fridge = fridgeRepository.findById(statAddItemToFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statAddItemToFridgeDTO.fridgeId())
        );
        StatType statType = statTypeRepository.findById(3L).orElseThrow(
                () -> new StatNotFoundException(3L)
        );

        statRepository.save(StatMapper.toStatistics(statAddItemToFridgeDTO, user, fridge, statType));
    }

    @Override
    public String getUserStats() {
        Long userId = checkUserIsAuthenticated();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        List<Statistics> stats = statRepository.findAllByUser(user);

        logger.info("Stats: " + stats);

        objectMapper.registerModule(new JavaTimeModule());
        String output;
        try {
            output = objectMapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse stats to JSON: " + e.getMessage());
        }
        return output;
    }

    @Override
    public String getFridgeStats(Long fridgeId) {
        Long userId = checkUserIsAuthenticated();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(
                () -> new FridgeNotFoundException(fridgeId)
        );

        if(!fridgeService.userExistsInFridge(fridge.getFridgeId(), user.getUsername())) {
            throw new UnauthorizedException(user.getUsername());
        }

        List<Statistics> stats = statRepository.findAllByFridge(fridge.getFridgeId());


        objectMapper.registerModule(new JavaTimeModule());
        String output;
        try {
            output = objectMapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse stats to JSON: " + e.getMessage());
        }
        return output;
    }

    @Override
    public String getAverageThrownPerDayUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        // Stats are sorted by date (timestamp)
        List<Statistics> stats = statRepository.findAllByUserAndStatType(userId, 1L);

        return statisticsToJsonThrowRate(stats);
    }

    @Override
    public String getAverageThrownPerDayFridge(long fridgeId) throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        // Check if user and fridge exist
        fridgeRepository.findByFridgeId(fridgeId).orElseThrow(
                () -> new FridgeNotFoundException(fridgeId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if(!fridgeService.userExistsInFridge(fridgeId, user.getUsername())) {
            throw new UnauthorizedException(user.getUsername());
        }

        // Stats are sorted by date (timestamp)
        List<Statistics> stats = statRepository.findAllByFridgeAndStatType(fridgeId, 1L);

        return statisticsToJsonThrowRate(stats);
    }

    @Override
    public String getAverageThrownTotalUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        return null;
    }

    @Override
    public String getAverageThrownTotalFridge(long fridgeId) throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();
        fridgeRepository.findByFridgeId(fridgeId).orElseThrow(
                () -> new FridgeNotFoundException(fridgeId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if(!fridgeService.userExistsInFridge(fridgeId, user.getUsername())) {
            throw new UnauthorizedException(user.getUsername());
        }
        return null;
    }

    @Override
    public String getMoneyWastedPerDayUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        List<Statistics> stats1 = statRepository.findAllByUserAndStatType(userId, 1L);
        List<Statistics> stats2 = statRepository.findAllByUserAndStatType(userId, 2L);
        return statisticsToJsonMoneyWasted(stats1, stats2);
    }

    @Override
    public String getMoneyWastedPerDayFridge(Long fridgeId) throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if(!fridgeService.userExistsInFridge(fridgeId, user.getUsername())) {
            throw new UnauthorizedException(user.getUsername());
        }

        List<Statistics> stats1 = statRepository.findAllByFridgeAndStatType(fridgeId, 1L);
        List<Statistics> stats2 = statRepository.findAllByFridgeAndStatType(fridgeId, 2L);
        return statisticsToJsonMoneyWasted(stats1, stats2);
    }

    @Override
    public String getMoneyUsedPerDayUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        List<Statistics> stats = statRepository.findAllByUserAndStatType(userId, 3L);
        return statisticsToJsonMoneySpent(stats);
    }

    @Override
    public String getMoneyUsedPerDayFridge(Long fridgeId) throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if(!fridgeService.userExistsInFridge(fridgeId, user.getUsername())) {
            throw new UnauthorizedException(user.getUsername());
        }

        List<Statistics> stats = statRepository.findAllByFridgeAndStatType(fridgeId, 3L);
        return statisticsToJsonMoneySpent(stats);
    }

    private long checkUserIsAuthenticated() {
        if(!jwtService.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        return jwtService.getAuthenticatedUserId();
    }

    private String statisticsToJsonThrowRate(List<Statistics> stats) throws JsonProcessingException {
        HashMap<String, Pair<Double, Integer>> averageThrownPerDayPair = new HashMap<>();
        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if(!averageThrownPerDayPair.containsKey(date)) {
                averageThrownPerDayPair.put(date, new Pair<>(stat.getStatValue()*stat.getQuantity(), stat.getQuantity()));
            } else {
                Pair<Double, Integer> pair = averageThrownPerDayPair.get(date);
                averageThrownPerDayPair.put(date, new Pair<>(pair.getFirst() + stat.getStatValue()*stat.getQuantity(), (int) pair.getSecond() + stat.getQuantity()));
            }
        }

        ArrayList<Pair<String, Double>> averageThrownPerDaySortedByDate = new ArrayList<>();
        HashSet<String> processedDates = new HashSet<>();

        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if (!processedDates.contains(date)) {
                Pair<Double, Integer> pair = averageThrownPerDayPair.get(date);
                averageThrownPerDaySortedByDate.add(new Pair<>(date, pair.getFirst() / pair.getSecond()));
                processedDates.add(date);
            }
        }

        return objectMapper.writeValueAsString(averageThrownPerDaySortedByDate);
    }

    private String statisticsToJsonMoneyWasted(List<Statistics> stats1, List<Statistics> stats2) throws JsonProcessingException {
        if(stats1.size() != stats2.size()) {
            // TODO: create custom exception for this?
            // If this happens, something is seriously wrong with the date in the database.
            throw new RuntimeException("Stats1 and stats2 are not the same size");
        }
        HashMap<String, Double> moneyWasted = new HashMap<>();
        int i = 0;
        for(Statistics stat : stats1) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if(!moneyWasted.containsKey(date)) {
                moneyWasted.put(date, stat.getStatValue() * (stats2.get(i).getStatValue()/100) * stat.getQuantity());
            } else {
                Double statValue = moneyWasted.get(date);
                moneyWasted.put(date, stat.getStatValue() * (stats2.get(i).getStatValue()/100) * stat.getQuantity() + statValue);
            }
            i++;
        }
        return objectMapper.writeValueAsString(moneyWasted);
    }

    private String statisticsToJsonMoneySpent(List<Statistics> stats) throws JsonProcessingException {
        HashMap<String, Double> moneySaved = new HashMap<>();
        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if(!moneySaved.containsKey(date)) {
                moneySaved.put(date, stat.getStatValue() * stat.getQuantity());
            } else {
                Double statValue = moneySaved.get(date);
                moneySaved.put(date, stat.getStatValue() * stat.getQuantity() + statValue);
            }
        }
        return objectMapper.writeValueAsString(moneySaved);
    }

    private String statisticsToJsonTotalThrowRate(List<Statistics> stats) throws JsonProcessingException {
        HashMap<String, Pair<Double, Integer>> averageThrownPerDayPair = new HashMap<>();
        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if(!averageThrownPerDayPair.containsKey(date)) {
                averageThrownPerDayPair.put(date, new Pair<>(stat.getStatValue()*stat.getQuantity(), stat.getQuantity()));
            } else {
                Pair<Double, Integer> pair = averageThrownPerDayPair.get(date);
                averageThrownPerDayPair.put(date, new Pair<>(pair.getFirst() + stat.getStatValue()*stat.getQuantity(), (int) pair.getSecond() + stat.getQuantity()));
            }
        }

        ArrayList<Pair<String, Double>> averageThrownPerDaySortedByDate = new ArrayList<>();
        HashSet<String> processedDates = new HashSet<>();

        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if (!processedDates.contains(date)) {
                Pair<Double, Integer> pair = averageThrownPerDayPair.get(date);
                averageThrownPerDaySortedByDate.add(new Pair<>(date, pair.getFirst() / pair.getSecond()));
                processedDates.add(date);
            }
        }

        return objectMapper.writeValueAsString(averageThrownPerDaySortedByDate);
    }
}

@Data
class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}