package edu.ntnu.idatt2106_2023_06.backend.service.apiKey;

import edu.ntnu.idatt2106_2023_06.backend.config.ApiKeyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiKeyService implements IApiKeyService {
    private ApiKeyConfig apiKeyConfig;



}
