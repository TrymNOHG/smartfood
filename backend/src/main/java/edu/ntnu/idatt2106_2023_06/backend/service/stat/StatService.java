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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service for handling statistics. This service is used to get statistics for a user, fridge or item. Can both generate
 * and get statistics.
 *
 * @author Brage H. Kvamme
 */
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

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Checks if a value of a given stat type is valid.
     *
     * @param statValue The value to check
     * @param statType The stat type to compare the value against
     */
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

    /**
     * Converts a list of statistics to a JSON string.
     *
     * @param statProvider A function that takes a user and returns a list of statistics
     * @return A JSON string of the statistics
     */
    private String getStatsJson(Function<User, List<Statistics>> statProvider) {
        Long userId = checkUserIsAuthenticated();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        List<Statistics> stats = statProvider.apply(user);

        logger.info("Stats: " + stats);

        try {
            return objectMapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse stats to JSON: " + e.getMessage());
        }
    }

    /**
     * Adds a new statistic to the database. This method is used when an item is deleted from a fridge.
     *
     * @param statDeleteFromFridgeDTO The DTO containing the information needed to create the statistic
     */
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

    /**
     * Adds a new statistic to the database. This method is used when an item is added to a fridge.
     *
     * @param statAddItemToFridgeDTO The DTO containing the information needed to create the statistic
     */
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

    /**
     * Gets the statistics for a user as JSON.
     *
     * @return A JSON string of the statistics
     */
    @Override
    public String getUserStats() {
        return getStatsJson(statRepository::findAllByUser);
    }

    /**
     * Gets the statistics for a fridge as JSON.
     *
     * @param fridgeId ID of the fridge
     * @return A JSON string of the statistics
     */
    @Override
    public String getFridgeStats(Long fridgeId) {
        return getStatsJson(stats -> statRepository.findAllByFridge(fridgeId));
    }

    /**
     * Gets the average thrown food per day for a user as JSON.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    @Override
    public String getAverageThrownPerDayUser() throws JsonProcessingException {
        return getAverageThrownPerDayJson(userId -> statRepository.findAllByUserAndStatType(userId, 1L));
    }

    /**
     * Gets the average thrown food per day for a fridge as JSON.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    @Override
    public String getAverageThrownPerDayFridge(long fridgeId) throws JsonProcessingException {
        return getAverageThrownPerDayJson(stats -> statRepository.findAllByFridgeAndStatType(fridgeId, 1L));
    }

    /**
     * Gets the average thrown food per day for a user as JSON. User has to be authenticated.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    private String getAverageThrownPerDayJson(Function<Long, List<Statistics>> statProvider) throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        List<Statistics> stats = statProvider.apply(userId);

        return statisticsToJsonThrowRate(stats);
    }

    /**
     * Gets the total average thrown by the user as JSON. User has to be authenticated.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    @Override
    public Double getAverageThrownTotalUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();
        List<Statistics> stats = statRepository.findAllByUserAndStatType(userId, 1L);
        return statisticsToJsonTotalThrowRate(stats);
    }

    /**
     * Gets the total average thrown in the fridge as JSON. User has to be authenticated and be a part
     * of the fridge.
     *
     * @param fridgeId ID of the fridge
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    @Override
    public Double getAverageThrownTotalFridge(long fridgeId) throws JsonProcessingException {
        checkValidUserInFridge(fridgeId);
        List<Statistics> stats = statRepository.findAllByFridgeAndStatType(fridgeId, 1L);
        return statisticsToJsonTotalThrowRate(stats);
    }

    /**
     * Checks if a user is both authenticated and a part of the fridge.
     *
     * @param fridgeId ID of the fridge to check
     */
    private void checkValidUserInFridge(long fridgeId) {
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
    }

    /**
     * Gets the total money wasted by throwing food by user. User has to be authenticated.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    @Override
    public String getMoneyWastedPerDayUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        List<Statistics> stats1 = statRepository.findAllByUserAndStatType(userId, 1L);
        List<Statistics> stats2 = statRepository.findAllByUserAndStatType(userId, 2L);
        return statisticsToJsonMoneyWasted(stats1, stats2);
    }

    /**
     * Gets the total money wasted by throwing food by fridge. User has to be authenticated and be a part
     * of the fridge.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
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

    /**
     * Gets the total money spent by user per day. User has to be authenticated.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    @Override
    public String getMoneyUsedPerDayUser() throws JsonProcessingException {
        Long userId = checkUserIsAuthenticated();

        List<Statistics> stats = statRepository.findAllByUserAndStatType(userId, 3L);
        return statisticsToJsonMoneySpent(stats);
    }

    /**
     * Gets the total money spent by fridge per day. User has to be authenticated and be a part
     * of the fridge to use this method.
     *
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
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

    /**
     * Checks if a user is authenticated with spring security. If not, an UnauthorizedException is thrown.
     *
     * @return The ID of the authenticated user
     */
    private long checkUserIsAuthenticated() {
        if(!jwtService.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        return jwtService.getAuthenticatedUserId();
    }

    /**
     * Converts a list of statistics to a JSON string of the average thrown per day.
     *
     * @param stats List of statistics
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    private String statisticsToJsonThrowRate(List<Statistics> stats) throws JsonProcessingException {
        HashMap<String, Pair<Double, Integer>> averageThrownPerDayPair = new HashMap<>();
        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            logger.info("Date: " + date);
            if(!averageThrownPerDayPair.containsKey(date)) {
                averageThrownPerDayPair.put(date, new Pair<>(stat.getStatValue()*stat.getQuantity(), stat.getQuantity()));
            } else {
                Pair<Double, Integer> pair = averageThrownPerDayPair.get(date);
                averageThrownPerDayPair.put(date, new Pair<>(pair.getFirst() + stat.getStatValue()*stat.getQuantity(), (int) pair.getSecond() + stat.getQuantity()));
            }
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String, Pair<Double, Integer>> sortedByDate = averageThrownPerDayPair.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey((dateStr1, dateStr2) -> {
                    LocalDate date1 = LocalDate.parse(dateStr1, dateFormatter);
                    LocalDate date2 = LocalDate.parse(dateStr2, dateFormatter);
                    return date1.compareTo(date2);
                }))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(sortedByDate);

        return objectMapper.writeValueAsString(sortedByDate);
    }

    /**
     * Converts a list of statistics to a JSON string of the average money spent per day.
     *
     * @param stats1 List of statistics
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String, Double> sortedByDate = moneyWasted.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey((dateStr1, dateStr2) -> {
                    LocalDate date1 = LocalDate.parse(dateStr1, dateFormatter);
                    LocalDate date2 = LocalDate.parse(dateStr2, dateFormatter);
                    return date1.compareTo(date2);
                }))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return objectMapper.writeValueAsString(sortedByDate);
    }

    /**
     * Converts a list of statistics to a JSON string of the money spent per day.
     *
     * @param stats List of statistics
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String, Double> sortedByDate = moneySaved.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey((dateStr1, dateStr2) -> {
                    LocalDate date1 = LocalDate.parse(dateStr1, dateFormatter);
                    LocalDate date2 = LocalDate.parse(dateStr2, dateFormatter);
                    return date1.compareTo(date2);
                }))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return objectMapper.writeValueAsString(sortedByDate);
    }

    /**
     * Converts a list of statistics to a JSON string of the average throw rate per day.
     *
     * @param stats List of statistics
     * @return A JSON string of the statistics
     */
    private double statisticsToJsonTotalThrowRate(List<Statistics> stats) {
        Pair<Double, Integer> pair = new Pair<>(0.0, 0);
        for(Statistics stat : stats) {
            pair.setFirst(pair.getFirst() + stat.getStatValue()*stat.getQuantity());
            pair.setSecond(pair.getSecond() + stat.getQuantity());
        }

        return pair.getFirst() / pair.getSecond();
    }
}

/**
 * The type Pair is a class for keeping track of a pair of two objects.
 *
 * @param <T> first object
 * @param <U> second object
 */
@Data
class Pair<T, U> {
    private T first;
    private U second;

    /**
     * Instantiates a new Pair of objects.
     *
     * @param first  the first object
     * @param second the second object
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}