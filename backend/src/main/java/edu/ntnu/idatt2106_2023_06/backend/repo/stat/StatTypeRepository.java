package edu.ntnu.idatt2106_2023_06.backend.repo.stat;

import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatTypeRepository extends JpaRepository<StatType, Long>, JpaSpecificationExecutor<StatType> {
}
