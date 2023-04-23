package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatService implements IStatService {

    private final JwtService jwtService;

    private void checkValidStatValue(int statValue, int statType) {
        switch (statType) {
            case 1:
                if(statValue < 0 || statValue > 100) {
                    throw new IllegalArgumentException("Stat value must be between 0 and 100");
                }
                break;
            case 2:
                if(statValue < 0) {
                    throw new IllegalArgumentException("Stat value must be positive");
                }

        }
    }

    @Override
    public void addStat(StatAddDTO statAddDTO) {
        Long userId = jwtService.getAuthenticatedUserId();
        if(!Objects.equals(userId, statAddDTO.userId())) {
            throw new UnauthorizedException(userId.toString());
        }

    }
}
