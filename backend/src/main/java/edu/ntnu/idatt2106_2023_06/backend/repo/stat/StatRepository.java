package edu.ntnu.idatt2106_2023_06.backend.repo.stat;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StatRepository extends JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics> {

    /**
     * Find all statistics by fridge ID.
     *
     * @param fridge The fridge entity.
     * @return A list of statistics for the specified fridge.
     */
    List<Statistics> findAllByFridge(Fridge fridge);

    /**
     * Find all statistics by user ID.
     *
     * @param user The user entity.
     * @return A list of statistics for the specified user.
     */
    List<Statistics> findAllByUser(User user);

}
