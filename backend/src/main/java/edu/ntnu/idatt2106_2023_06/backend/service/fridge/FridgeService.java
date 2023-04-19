package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

import edu.ntnu.idatt2106_2023_06.backend.model.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final Logger logger = LoggerFactory.getLogger(FridgeService.class);


    @PostConstruct
    public void init() {
        fridgeRepository.dropTrigger();
        fridgeRepository.createTrigger();
    }

}
