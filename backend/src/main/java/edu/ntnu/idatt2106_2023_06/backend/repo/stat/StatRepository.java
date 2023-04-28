package edu.ntnu.idatt2106_2023_06.backend.repo.stat;

import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatRepository extends JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics> {

    /**
     * Find all statistics by fridge ID.
     *
     * @param fridgeId The fridge ID.
     * @return A list of statistics for the specified fridge.
     */
    @Query("SELECT s FROM Statistics s WHERE s.fridge.fridgeId = :fridgeId")
    List<Statistics> findAllByFridge(@Param("fridgeId") Long fridgeId);

    @Query("SELECT s FROM Statistics s WHERE s.statType.statTypeId = :statTypeId AND s.fridge.fridgeId = :fridgeId ORDER BY s.timestamp ASC")
    List<Statistics> findAllByFridgeAndStatType1(@Param("fridgeId") Long fridgeId, @Param("statTypeId") Long statTypeId);


    @Query("SELECT s FROM Statistics s WHERE s.statType.statTypeId = :statTypeId AND s.user.userId = :userId ORDER BY s.timestamp ASC")
    List<Statistics> findAllByUserAndStatType1(@Param("userId") Long userId, @Param("statTypeId") Long statTypeId);

    /**
     * Find all statistics by user ID.
     *
     * @param user The user entity.
     * @return A list of statistics for the specified user.
     */
    List<Statistics> findAllByUser(User user);

}
