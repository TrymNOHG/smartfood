package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.illegal.IllegalStatValueException;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.StatNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.StatMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
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
    private final ItemRepository itemRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Checks if a value of a given stat type is valid.
     *
     * @param statValue The value to check
     */
    private void checkValidStatValue(double statValue) {
        if (statValue < 0) {
            throw new IllegalStatValueException();
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

        checkValidStatValue(statDeleteFromFridgeDTO.amountDeleted());

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Fridge fridge = fridgeRepository.findById(statDeleteFromFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statDeleteFromFridgeDTO.fridgeId())
        );
        StatType statType1 = statTypeRepository.findById(1L).orElseThrow(
                () -> new StatNotFoundException(1L)
        );

        statRepository.saveAll(StatMapper.toStatistics(statDeleteFromFridgeDTO, user, fridge, statType1));
    }

    /**
     * Adds a new statistic to the database. This method is used when an item is added to a fridge.
     *
     * @param statAddItemToFridgeDTO The DTO containing the information needed to create the statistic
     */
    @Override
    public void statAddItemToFridge(StatAddItemToFridgeDTO statAddItemToFridgeDTO) {
        Long userId = checkUserIsAuthenticated();

        checkValidStatValue(statAddItemToFridgeDTO.price());

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

        return statisticsToJsonThrowAmount(stats);
    }

    /**
     * Gets the total average thrown by the user as JSON. User has to be authenticated.
     *
     * @return A JSON string of the statistics
     */
    @Override
    public Double getAverageThrownTotalUser() {
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
     */
    @Override
    public Double getAverageThrownTotalFridge(long fridgeId) {
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
        return statisticsToJsonMoneyWasted(stats1);
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
        return statisticsToJsonMoneyWasted(stats1);
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
    private String statisticsToJsonThrowAmount(List<Statistics> stats) throws JsonProcessingException {
        logger.info("Statistics: " + stats);
        HashMap<String, Double> averageThrownPerDayAmount = new HashMap<>();
        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            if(!averageThrownPerDayAmount.containsKey(date)) {
                averageThrownPerDayAmount.put(date, (stat.getStatValue()));
            } else {
                averageThrownPerDayAmount.put(date, stat.getStatValue()+ averageThrownPerDayAmount.get(date));
            }
        }
        logger.info("Average thrown per day: " + averageThrownPerDayAmount);
        return objectMapper.writeValueAsString(sortHashMap(averageThrownPerDayAmount));
    }

    /**
     * Converts a list of statistics to a JSON string of the average money spent per day.
     *
     * @param stats List of statistics
     * @return A JSON string of the statistics
     * @throws JsonProcessingException If the statistics could not be parsed to JSON
     */
    private String statisticsToJsonMoneyWasted(List<Statistics> stats) throws JsonProcessingException {
        HashMap<String, Double> moneyWasted = new HashMap<>();
        for(Statistics stat : stats) {
            String date = stat.getTimestamp().toString().substring(0, 10);
            Item item = itemRepository.findItemByProductNameAndStore_StoreName(stat.getItemName(), stat.getStoreName()).orElseThrow(
                    () -> new ItemNotFoundException(stat.getItemName())
            );
            double fullAmount;
            double price = item.getPrice();
            if(item.getUnit().equals("pieces")) {
                fullAmount = item.getAmount()*250;
            } else {
                fullAmount = item.getAmount();
            }
            double thrownAmount = stat.getStatValue();
            double percentage = (thrownAmount/fullAmount);
            if (!moneyWasted.containsKey(date)) {
                logger.info("price: " + price + " percentage: " + percentage + " date: " + date);
                logger.info("Money wasted does not contain: " + moneyWasted);
                moneyWasted.put(date, price * percentage);
            } else {
                logger.info("Money wasted does contain: " + moneyWasted);
                Double statValue = moneyWasted.get(date);
                moneyWasted.put(date, statValue + price * percentage);
            }
        }
        logger.info("Money wasted: " + moneyWasted);
        return objectMapper.writeValueAsString(sortHashMap(moneyWasted));
    }

    /**
     * Sorts a hashmap by date and converts it to a LinkedHashMap.
     *
     * @param map The hashmap to sort
     * @return A LinkedHashMap sorted by date
     */
    private LinkedHashMap<String, Double> sortHashMap(Map<String, Double> map) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return map.entrySet()
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
                moneySaved.put(date, stat.getStatValue());
            } else {
                Double statValue = moneySaved.get(date);
                moneySaved.put(date, stat.getStatValue() + statValue);
            }
        }
        return objectMapper.writeValueAsString(sortHashMap(moneySaved));
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
            pair.setFirst(pair.getFirst() + stat.getStatValue());
            pair.setSecond(pair.getSecond());
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